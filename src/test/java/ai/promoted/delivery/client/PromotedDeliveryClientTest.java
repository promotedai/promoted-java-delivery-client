package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ai.promoted.delivery.model.DeliveryLog;
import ai.promoted.delivery.model.LogRequest;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;
import ai.promoted.proto.event.CohortArm;
import ai.promoted.proto.event.CohortMembership;
import ai.promoted.proto.common.ClientInfo.ClientType;
import ai.promoted.proto.common.ClientInfo.TrafficType;
import ai.promoted.proto.delivery.ExecutionServer;
import ai.promoted.proto.delivery.Insertion;

@ExtendWith(MockitoExtension.class)
class PromotedDeliveryClientTest {

  private TestApiFactory apiFactory;

  @BeforeEach
  void init(@Mock Delivery sdkDelivery, @Mock Delivery apiDelivery, @Mock Metrics apiMetrics) {
    apiFactory = new TestApiFactory(sdkDelivery, apiDelivery, apiMetrics);
  }

  @Test
  void testPerformChecksFalseDoesNotCallValidator() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();

    DeliveryRequestValidator mockValidator = Mockito.mock(DeliveryRequestValidator.class);
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0, mockValidator);

    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

    DeliveryResponse resp = client.deliver(dreq);
    assertNotNull(resp);

    verifyNoInteractions(mockValidator);
  }

  @Test
  void testPerformChecksTrueDoesCallValidator() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();
    client.setPerformChecks(true);

    DeliveryRequestValidator mockValidator = Mockito.mock(DeliveryRequestValidator.class);
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0, mockValidator);

    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

    DeliveryResponse resp = client.deliver(dreq);
    assertNotNull(resp);

    verify(mockValidator, times(1)).validate(dreq);
  }

  @Test
  void testOnlyLogCallsSDKDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();

    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

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
    PromotedDeliveryClient client = PromotedDeliveryClient.builder().withExecutor(Runnable::run)
        .withApplyTreatmentChecker((cm) -> false).withApiFactory(apiFactory).build();

    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

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
    PromotedDeliveryClient client = PromotedDeliveryClient.builder().withExecutor(Runnable::run)
        .withApplyTreatmentChecker((cm) -> true).withApiFactory(apiFactory).build();

    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);

    when(apiFactory.getApiDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

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

    CohortMembership cm = CohortMembership.newBuilder().setArm(CohortArm.TREATMENT).setCohortId("testing").build();
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, 0);

    when(apiFactory.getApiDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

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
  void testBlockingShadowTrafficCallsAPIDeliveryAndSDKDeliveryAndLogs() throws Exception {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder().withExecutor(Runnable::run)
        .withApiFactory(apiFactory).withBlockingShadowTraffic(true)
        .withShadowTrafficDeliveryRate((float) 1.0).build();

    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));

    // With no experiment, onlyLog=true to cause SDK-side delivery and thus the possiblity of shadow traffic.
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0);

    when(apiFactory.getApiDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());

    // For shadow traffic, we call BOTH SDK delivery (to return results) AND API delivery (for shadow traffic).
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);

    // Shadow traffic sends a modified delivery request.
    ArgumentCaptor<DeliveryRequest> shadowRequestRequestCaptor = ArgumentCaptor.forClass(DeliveryRequest.class);
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(shadowRequestRequestCaptor.capture());
    DeliveryRequest shadowRequest = shadowRequestRequestCaptor.getValue();
    assertEquals(TrafficType.SHADOW, shadowRequest.getRequest().getClientInfo().getTrafficType());
    assertEquals(ClientType.PLATFORM_SERVER, shadowRequest.getRequest().getClientInfo().getClientType());
    assertSame(dreq.getRequest().getInsertion(), shadowRequest.getRequest().getInsertion());

    // Cohort membership and server side delivery -> follow-up logging.
    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    assertSDKLogRequest(req, resp.getResponse(), logRequest);
    assertDeliveryResponse(resp, ExecutionServer.SDK);
  }

  @Test
  void testNullCohortMembershipArmIsTreatment() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();

    CohortMembership cm = CohortMembership.newBuilder().setCohortId("testing").build();
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, 0);

    when(apiFactory.getApiDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

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

    CohortMembership cm = CohortMembership.newBuilder().setArm(CohortArm.CONTROL).setCohortId("testing").build();
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

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
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);


    when(apiFactory.getApiDelivery().runDelivery(any())).thenThrow(DeliveryException.class);
    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

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

  @Test
  void testPassThroughSdkInsertionIds() throws Exception {
    PromotedDeliveryClient client = createDefaultClient();

    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    List<Insertion> insertions = req.getInsertion();
    for (int i = 0; i < insertions.size(); i++) {
      insertions.set(i, insertions.get(i).toBuilder().setInsertionId("ins" + i).build());
    }
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any()))
        .thenReturn(new Response().insertion(req.getInsertion()));

    DeliveryResponse resp = client.deliver(dreq);
    assertTrue(req.getClientRequestId().length() > 0);

    assertEquals(10, resp.getResponse().getInsertion().size());
    assertEquals("ins0", resp.getResponse().getInsertion().get(0).getInsertionId());
    assertEquals("ins1", resp.getResponse().getInsertion().get(1).getInsertionId());
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
    verify(apiFactory.getApiMetrics(), times(1)).runMetricsLogging(any());

    ArgumentCaptor<LogRequest> logRequestCaptor = ArgumentCaptor.forClass(LogRequest.class);
    verify(apiFactory.getApiMetrics()).runMetricsLogging(logRequestCaptor.capture());
    LogRequest logRequest = logRequestCaptor.getValue();

    assertSDKLogRequest(req, resp.getResponse(), logRequest);
    assertDeliveryResponse(resp, ExecutionServer.SDK);
  }

  private PromotedDeliveryClient createDefaultClient() {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder().withExecutor(Runnable::run)
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
