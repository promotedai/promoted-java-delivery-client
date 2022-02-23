package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;

class DeliveryRequestStateTest {

  @Test
  void testExactlyMaxRequestInsertions() {
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    
    DeliveryRequestState state = new DeliveryRequestState(dreq);
    Request toSend = state.getRequestToSend(10);
    assertEquals(10, toSend.getInsertion().size());
    
    Response fromApi = new Response().insertion(TestUtils.createTestResponseInsertions(10, 0));
    Response toReturn = state.getResponseToReturn(fromApi);
    assertEquals(10, toReturn.getInsertion().size());
  }
  
  @Test
  void testMoreThanMaxRequestInsertions() {
    Request req = new Request().insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    
    DeliveryRequestState state = new DeliveryRequestState(dreq);
    Request toSend = state.getRequestToSend(5);
    assertEquals(5, toSend.getInsertion().size());
    
    Response fromApi = new Response().insertion(TestUtils.createTestResponseInsertions(5, 0));
    Response toReturn = state.getResponseToReturn(fromApi);
    assertEquals(5, toReturn.getInsertion().size());
  }
}
