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
import ai.promoted.proto.event.CohortArm;
import ai.promoted.proto.event.CohortMembership;
import ai.promoted.proto.common.ClientInfo;
import ai.promoted.proto.common.ClientInfo.TrafficType;
import ai.promoted.proto.delivery.Request;
import ai.promoted.proto.delivery.Response;

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
    
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(TestUtils.createTestRequestInsertions(10)).setClientInfo(ClientInfo.newBuilder().setTrafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(Response.newBuilder().addAllInsertion(reqBuilder.getInsertionList()).build());
    
    client.deliver(dreq);
    
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(any());

    ArgumentCaptor<DeliveryRequest> shadowTrafficRequestCaptor = ArgumentCaptor.forClass(DeliveryRequest.class);
    verify(apiFactory.getApiDelivery()).runDelivery(shadowTrafficRequestCaptor.capture());
    DeliveryRequest shadowTrafficRequest = shadowTrafficRequestCaptor.getValue();
    assertEquals(TrafficType.SHADOW, shadowTrafficRequest.getRequestBuilder().getClientInfo().getTrafficType());
  }  
  
  @Test
  void testSendShadowTrafficForUserInControl() throws Exception {
    PromotedDeliveryClient client = createDefaultClient(true, 0.5f);
    CohortMembership cm = CohortMembership.newBuilder().setArm(CohortArm.CONTROL).setCohortId("testing").build();

    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(TestUtils.createTestRequestInsertions(10)).setClientInfo(ClientInfo.newBuilder().setTrafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, cm, false, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(Response.newBuilder().addAllInsertion(reqBuilder.getInsertionList()).build());
    
    client.deliver(dreq);
    
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(any());

    ArgumentCaptor<DeliveryRequest> shadowTrafficRequestCaptor = ArgumentCaptor.forClass(DeliveryRequest.class);
    verify(apiFactory.getApiDelivery()).runDelivery(shadowTrafficRequestCaptor.capture());
    DeliveryRequest shadowTrafficRequest = shadowTrafficRequestCaptor.getValue();
    assertEquals(TrafficType.SHADOW, shadowTrafficRequest.getRequestBuilder().getClientInfo().getTrafficType());
  }  
  
  @Test
  void testDontSendShadowTrafficForOnlyLogSampledOut() throws Exception {
    PromotedDeliveryClient client = createDefaultClient(false, 0.5f);
    
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(TestUtils.createTestRequestInsertions(10)).setClientInfo(ClientInfo.newBuilder().setTrafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(Response.newBuilder().addAllInsertion(reqBuilder.getInsertionList()).build());
    
    client.deliver(dreq);
    
    verify(apiFactory.getSdkDelivery(), times(1)).runDelivery(dreq);
    verifyNoInteractions(apiFactory.getApiDelivery());
  }
  
  @Test
  void testDontSendShadowTrafficForUserInTreatment() throws Exception {
    // This case calls normal delivery successfully.
    PromotedDeliveryClient client = createDefaultClient(false, 0.5f);
    CohortMembership cm = CohortMembership.newBuilder().setArm(CohortArm.TREATMENT).setCohortId("testing").build();

    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(TestUtils.createTestRequestInsertions(10)).setClientInfo(ClientInfo.newBuilder().setTrafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, cm, false, 0);

    when(apiFactory.getApiDelivery().runDelivery(any())).thenReturn(Response.newBuilder().addAllInsertion(reqBuilder.getInsertionList()).build());
    
    client.deliver(dreq);
    
    verify(apiFactory.getApiDelivery(), times(1)).runDelivery(dreq);
  }
  
  @Test
  void testDontSendShadowTrafficForOnlyLogWhenTurnedOff() throws Exception {
    PromotedDeliveryClient client = createDefaultClient(true, 0f);
    
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(TestUtils.createTestRequestInsertions(10)).setClientInfo(ClientInfo.newBuilder().setTrafficType(TrafficType.PRODUCTION));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, true, 0);

    when(apiFactory.getSdkDelivery().runDelivery(any())).thenReturn(Response.newBuilder().addAllInsertion(reqBuilder.getInsertionList()).build());
    
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
