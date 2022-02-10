package ai.promoted.java.client;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

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
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withMetricsExecutor(new CurrentThreadExecutor())
        .withApiFactory(apiFactory).build();
    
    Request req = new Request().insertion(TestUtils.createInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, null);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    Response resp = client.deliver(dreq);
    
    assertEquals(10, resp.getInsertion().size());
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
    verify(apiFactory.getApiMetrics(), times(1)).runMetricsLogging(any());
    
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();
    
    assertEquals(1, logRequest.getDeliveryLog().size());
    DeliveryLog deliveryLog = logRequest.getDeliveryLog().get(0);
    assertEquals(req, deliveryLog.getRequest());
    assertEquals(resp, deliveryLog.getResponse());
    assertEquals(ExecutionServer.SDK, deliveryLog.getExecution().getExecutionServer());
  }
  
  @Test
  void testCustomNotShouldApplyTreatmentCallsSDKDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withMetricsExecutor(new CurrentThreadExecutor())
        .withApplyTreatmentChecker((cm) -> false)
        .withApiFactory(apiFactory).build();
    
    Request req = new Request().insertion(TestUtils.createInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    Response resp = client.deliver(dreq);
    
    assertEquals(10, resp.getInsertion().size());
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
    verify(apiFactory.getApiMetrics(), times(1)).runMetricsLogging(any());
    
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    assertEquals(1, logRequest.getDeliveryLog().size());
    DeliveryLog deliveryLog = logRequest.getDeliveryLog().get(0);
    assertEquals(req, deliveryLog.getRequest());
    assertEquals(resp, deliveryLog.getResponse());
    assertEquals(ExecutionServer.SDK, deliveryLog.getExecution().getExecutionServer());
  }
  
  @Test
  void testCustomShouldApplyTreatmentCallsAPIDeliveryAndDoesNotLog() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withMetricsExecutor(new CurrentThreadExecutor())
        .withApplyTreatmentChecker((cm) -> true)
        .withApiFactory(apiFactory).build();
    
    Request req = new Request().insertion(TestUtils.createInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);

    when(apiFactory.getApiDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));

    Response resp = client.deliver(dreq);
    
    assertEquals(10, resp.getInsertion().size());
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getSdkDelivery());
    
    // No cohort membership and server side delivery -> no follow-up logging.
    verifyNoInteractions(apiFactory.getApiMetrics());
  }
  
  @Test
  void testHasTreatmentCohortMembershipCallsAPIDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withMetricsExecutor(new CurrentThreadExecutor())
        .withApiFactory(apiFactory).build();
    
    CohortMembership cm = new CohortMembership().arm(CohortArm.TREATMENT).cohortId("testing");
    Request req = new Request().insertion(TestUtils.createInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, null);

    when(apiFactory.getApiDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    Response resp = client.deliver(dreq);
    
    assertEquals(10, resp.getInsertion().size());
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getSdkDelivery());
    
    // Cohort membership and server side delivery -> follow-up logging.
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    // No need to send a delivery log since delivery happened server-side.
    assertNull(logRequest.getDeliveryLog());
  }

  @Test
  void testHasControlCohortMembershipCallsSDKDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withMetricsExecutor(new CurrentThreadExecutor())
        .withApiFactory(apiFactory).build();
    
    CohortMembership cm = new CohortMembership().arm(CohortArm.CONTROL).cohortId("testing");
    Request req = new Request().insertion(TestUtils.createInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, null);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    Response resp = client.deliver(dreq);
    
    assertEquals(10, resp.getInsertion().size());
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
    
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    // Send a delivery log since delivery happened in the SDK.
    assertEquals(1, logRequest.getDeliveryLog().size());
    DeliveryLog deliveryLog = logRequest.getDeliveryLog().get(0);
    assertEquals(req, deliveryLog.getRequest());
    assertEquals(resp, deliveryLog.getResponse());
    assertEquals(ExecutionServer.SDK, deliveryLog.getExecution().getExecutionServer());
  }

}
