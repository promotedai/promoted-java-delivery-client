package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ai.promoted.delivery.model.CohortArm;
import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.DeliveryLog;
import ai.promoted.delivery.model.ExecutionServer;
import ai.promoted.delivery.model.LogRequest;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;

@ExtendWith(MockitoExtension.class)
class PromotedDeliveryClientTest {

  private TestApiFactory apiFactory;
  
  @BeforeEach
  void init(@Mock Delivery sdkDelivery, @Mock Delivery apiDelivery, @Mock Metrics apiMetrics) {
    apiFactory = new TestApiFactory(sdkDelivery, apiDelivery, apiMetrics);
  }
  
  @Test
  void testOnlyLogCallsSDKDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();
    
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, null);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);
    
    assertEquals(10, resp.getResponse().getInsertion().size());
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
    verify(apiFactory.getApiMetrics(), times(1)).runMetricsLogging(any());
    
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();
    
    assertSDKLogRequest(req, resp.getResponse(), logRequest);
    assertDeliveryResponse(resp, ExecutionServer.SDK);
  }


  @Test
  void testCustomNotShouldApplyTreatmentCallsSDKDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withExecutor(Runnable::run)
        .withApplyTreatmentChecker((cm) -> false)
        .withApiFactory(apiFactory).build();
    
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
    verify(apiFactory.getApiMetrics(), times(1)).runMetricsLogging(any());
    
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    assertSDKLogRequest(req, resp.getResponse(), logRequest);
    assertDeliveryResponse(resp, ExecutionServer.SDK);
}
  
  @Test
  void testCustomShouldApplyTreatmentCallsAPIDeliveryAndDoesNotLog() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withExecutor(Runnable::run)
        .withApplyTreatmentChecker((cm) -> true)
        .withApiFactory(apiFactory).build();
    
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);

    when(apiFactory.getApiDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));

    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getSdkDelivery());
    
    // No cohort membership and server side delivery -> no follow-up logging.
    verifyNoInteractions(apiFactory.getApiMetrics());
    assertDeliveryResponse(resp, ExecutionServer.API);
}
  
  @Test
  void testHasTreatmentCohortMembershipCallsAPIDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();
    
    CohortMembership cm = new CohortMembership().arm(CohortArm.TREATMENT).cohortId("testing");
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, null);

    when(apiFactory.getApiDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getSdkDelivery());
    
    // Cohort membership and server side delivery -> follow-up logging.
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    // No need to send a delivery log since delivery happened server-side.
    assertNull(logRequest.getDeliveryLog());
    assertDeliveryResponse(resp, ExecutionServer.API);
  }  
  
  @Test
  void testNullCohortMembershipArmIsTreatment() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();
    
    CohortMembership cm = new CohortMembership().cohortId("testing");
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, null);

    when(apiFactory.getApiDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getSdkDelivery());
    
    // Cohort membership and server side delivery -> follow-up logging.
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    // No need to send a delivery log since delivery happened server-side.
    assertNull(logRequest.getDeliveryLog());
    assertDeliveryResponse(resp, ExecutionServer.API);
  }

  @Test
  void testHasControlCohortMembershipCallsSDKDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();
    
    CohortMembership cm = new CohortMembership().arm(CohortArm.CONTROL).cohortId("testing");
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, null);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
    
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    assertSDKLogRequest(req, resp.getResponse(), logRequest);
    assertDeliveryResponse(resp, ExecutionServer.SDK);
  }

  @Test
  void testApiDeliveryErrorFallsBackToSdkDelivery() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();
    
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);


    when(apiFactory.getApiDelivery().runDelivery(any())).thenThrow(DeliveryException.class);
    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));

    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    assertSDKLogRequest(req, resp.getResponse(), logRequest);
    assertDeliveryResponse(resp, ExecutionServer.SDK);
  }

  private PromotedDeliveryClient createDefaultClient() {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withExecutor(Runnable::run)
        .withApiFactory(apiFactory).build();
    return client;
  }
  
  private void assertSDKLogRequest(Request req, Response resp, LogRequest logRequest) {
    assertEquals(1, logRequest.getDeliveryLog().size());
    DeliveryLog deliveryLog = logRequest.getDeliveryLog().get(0);
    assertEquals(req, deliveryLog.getRequest());
    assertEquals(resp, deliveryLog.getResponse());
    assertEquals(ExecutionServer.SDK, deliveryLog.getExecution().getExecutionServer());
  }
  
  private void assertDeliveryResponse(DeliveryResponse resp, ExecutionServer sdk) {
    assertEquals(sdk, resp.getExecutionServer());
    assertTrue(resp.getClientRequestId().length() > 0);
  }
}
