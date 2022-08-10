package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
import ai.promoted.delivery.model.ClientInfo;
import ai.promoted.delivery.model.CohortArm;
import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;
import ai.promoted.delivery.model.TrafficType;

@ExtendWith(MockitoExtension.class)
class ShadowTrafficTest {

  private TestApiFactory apiFactory;
  
  @BeforeEach
  void init(@Mock Delivery sdkDelivery, @Mock Delivery apiDelivery, @Mock Metrics apiMetrics) {
    apiFactory = new TestApiFactory(sdkDelivery, apiDelivery, apiMetrics);
  }
  
  @Test
  void testSendShadowTrafficForOnlyLogSampledIn() throws Exception {
    PromotedDeliveryClient client = createDefaultClient(true, 0.5f);
    
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10)).clientInfo(new ClientInfo().trafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    client.deliver(dreq);
    
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(any());

    ArgumentCaptor<DeliveryRequest> shadowTrafficRequestCaptor = ArgumentCaptor.forClass(DeliveryRequest.class);
    verify(apiFactory.getApiDelivery()).runDelivery(shadowTrafficRequestCaptor.capture());
    DeliveryRequest shadowTrafficRequest = shadowTrafficRequestCaptor.getValue();
    assertEquals(TrafficType.SHADOW, shadowTrafficRequest.getRequest().getClientInfo().getTrafficType());
  }  
  
  @Test
  void testSendShadowTrafficForUserInControl() throws Exception {
    PromotedDeliveryClient client = createDefaultClient(true, 0.5f);
    CohortMembership cm = new CohortMembership().arm(CohortArm.CONTROL).cohortId("testing");

    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10)).clientInfo(new ClientInfo().trafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    client.deliver(dreq);
    
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(any());

    ArgumentCaptor<DeliveryRequest> shadowTrafficRequestCaptor = ArgumentCaptor.forClass(DeliveryRequest.class);
    verify(apiFactory.getApiDelivery()).runDelivery(shadowTrafficRequestCaptor.capture());
    DeliveryRequest shadowTrafficRequest = shadowTrafficRequestCaptor.getValue();
    assertEquals(TrafficType.SHADOW, shadowTrafficRequest.getRequest().getClientInfo().getTrafficType());
  }  
  
  @Test
  void testDontSendShadowTrafficForOnlyLogSampledOut() throws Exception {
    PromotedDeliveryClient client = createDefaultClient(false, 0.5f);
    
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10)).clientInfo(new ClientInfo().trafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    client.deliver(dreq);
    
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
  }
  
  @Test
  void testDontSendShadowTrafficForUserInTreatment() throws Exception {
    // This case calls normal delivery successfully.
    PromotedDeliveryClient client = createDefaultClient(false, 0.5f);
    CohortMembership cm = new CohortMembership().arm(CohortArm.TREATMENT).cohortId("testing");

    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10)).clientInfo(new ClientInfo().trafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(req, cm, false, 0);

    when(apiFactory.getApiDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    client.deliver(dreq);
    
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
  }
  
  @Test
  void testDontSendShadowTrafficForOnlyLogWhenTurnedOff() throws Exception {
    PromotedDeliveryClient client = createDefaultClient(true, 0f);
    
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10)).clientInfo(new ClientInfo().trafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(req, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(new Response().insertion(req.getInsertion()));
    
    client.deliver(dreq);
    
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
  }

  private PromotedDeliveryClient createDefaultClient(boolean samplesIn, float shadowTrafficRate) {
    PromotedDeliveryClient client = PromotedDeliveryClient.builder()
        .withExecutor(Runnable::run)
        .withShadowTrafficDeliveryRate(shadowTrafficRate)
        .withSampler(new FakeSampler(samplesIn))
        .withApiFactory(apiFactory).build();
    return client;
  }
}
