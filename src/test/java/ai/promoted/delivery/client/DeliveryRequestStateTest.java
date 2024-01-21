package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.proto.delivery.Request;
import ai.promoted.proto.delivery.Response;

class DeliveryRequestStateTest {

  @Test
  void testExactlyMaxRequestInsertions() {
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 0);
    
    DeliveryRequestState state = new DeliveryRequestState(dreq);
    Request toSend = state.getRequestToSend(10);
    assertEquals(10, toSend.getInsertionCount());
    
    Response fromApi = Response.newBuilder().addAllInsertion(TestUtils.createTestResponseInsertions(10, 0)).build();
    Response toReturn = state.getResponseToReturn(fromApi);
    assertEquals(10, toReturn.getInsertionCount());
  }

  @Test
  void testMoreThanMaxRequestInsertions() {
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 0);
    
    DeliveryRequestState state = new DeliveryRequestState(dreq);
    Request toSend = state.getRequestToSend(5);
    assertEquals(5, toSend.getInsertionCount());
    
    Response fromApi = Response.newBuilder().addAllInsertion(TestUtils.createTestResponseInsertions(5, 0)).build();
    Response toReturn = state.getResponseToReturn(fromApi);
    assertEquals(5, toReturn.getInsertionCount());
  }
}
