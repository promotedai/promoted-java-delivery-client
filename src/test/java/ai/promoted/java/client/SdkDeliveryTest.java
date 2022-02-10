package ai.promoted.java.client;

import static org.junit.jupiter.api.Assertions.*;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Paging;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;

class SdkDeliveryTest {

  @Test
  void testInvalidPagingOffset() {
    Request req = new Request().paging(new Paging().offset(10)).insertion(createInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    Exception exception = assertThrows(
        DeliveryException.class, 
        () -> new SdkDelivery().runDelivery(dreq));
        
    assertTrue(exception.getMessage().contains("Invalid paging"));
  }

  @Test
  void testNoPagingReturnsAll() throws Exception {
    List<Insertion> insertions = createInsertions(10);
    Request req = new Request().insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertAllResponseInsertions(insertions, resp);
  }
  
  @Test
  void testPagingZeroSizeReturnsAll() throws Exception {
    List<Insertion> insertions = createInsertions(10);
    Request req = new Request().paging(new Paging().offset(0).size(0)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertAllResponseInsertions(insertions, resp);
  }

  @Test
  void testPagingZeroOffset() throws Exception {
    List<Insertion> insertions = createInsertions(10);
    Request req = new Request().paging(new Paging().offset(0).size(5)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertEquals(5, resp.getInsertion().size());
    for (int i = 0; i < 5; i++) {
      Insertion insertion = insertions.get(i);
      assertEquals(i, insertion.getPosition());
    }
  }
  
  @Test
  void testPagingNonZeroOffset() throws Exception {
    List<Insertion> insertions = createInsertions(10);
    Request req = new Request().paging(new Paging().offset(5).size(5)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertEquals(5, resp.getInsertion().size());
    for (int i = 5; i < 10; i++) {
      Insertion insertion = insertions.get(i);
      assertEquals(i, insertion.getPosition());
    }
  }
  
  @Test
  void testPagingSizeMoreThanInsertions() throws Exception {
    List<Insertion> insertions = createInsertions(10);
    Request req = new Request().paging(new Paging().offset(0).size(11)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, null);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertAllResponseInsertions(insertions, resp);
  }
  
  @Test
  void testPrepaged() throws Exception {
    List<Insertion> insertions = createInsertions(10);
    Request req = new Request().paging(new Paging().offset(5)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, InsertionPageType.PREPAGED);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertEquals(10, resp.getInsertion().size());
    for (int i = 0; i < 10; i++) {
      Insertion insertion = insertions.get(i);
      assertEquals(i+5, insertion.getPosition());
    }
  }

  private void assertAllResponseInsertions(List<Insertion> insertions, Response resp) {
    assertEquals(10, resp.getInsertion().size());
    for (int i = 0; i < 10; i++) {
      Insertion insertion = insertions.get(i);
      assertEquals(i, insertion.getPosition());
      assertTrue(insertion.getInsertionId().length() > 0);
    }
  }
  
  private List<Insertion> createInsertions(int num) {
    List<Insertion> res = new ArrayList<>();
    for (int i = 0; i < num; i++) {
      res.add(new Insertion().contentId("" + i));
    }
    return res;
  }
}
