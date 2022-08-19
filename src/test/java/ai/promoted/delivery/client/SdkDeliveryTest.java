package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Paging;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;

class SdkDeliveryTest {

  @Test
  void testInvalidPagingOffsetAndInsertionStart() {
    Request req = new Request().paging(new Paging().offset(10).size(5)).insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 100);
    Exception exception = assertThrows(
        DeliveryException.class, 
        () -> new SdkDelivery().runDelivery(dreq));
        
    assertTrue(exception.getMessage().contains("offset should be >= insertion start"));
  }

  @Test
  void testValidPagingOffsetAndInsertionStart() throws DeliveryException {
    Request req = new Request().paging(new Paging().offset(10).size(5)).insertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 5);
    Response resp = new SdkDelivery().runDelivery(dreq);
    assertNotNull(resp);
  }

  @Test
  void testNoPagingReturnsAll() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request req = new Request().insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertAllResponseInsertions(insertions, resp);
  }
  
  @Test
  void testInsertionStartSetToOffset() throws DeliveryException {
    int insertionStart = 5;
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(3);
    Request req = new Request().insertion(insertions).paging(new Paging().size(2).offset(5));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, insertionStart);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertEquals(2, resp.getInsertion().size());
  }

  @Test
  void testInsertionStartLessThanOffset() throws DeliveryException {
    int insertionStart = 5;
    int offsetDiff = 1;
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(3);
    Request req = new Request().insertion(insertions).paging(new Paging().size(2).offset(6));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, insertionStart);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertEquals(2, resp.getInsertion().size());

    // Returns positions 1 and 2 since we want an offset one past the request insertion start.
    for (int i = 0; i < resp.getInsertion().size(); i++) {
      Insertion ins = resp.getInsertion().get(i);
      assertEquals(insertionStart + offsetDiff + i, ins.getPosition());
      assertEquals("" + (i + offsetDiff), ins.getContentId());
    }
  }

  @Test
  void testInsertionStartWithOffsetOutsideSize() throws DeliveryException {
    int insertionStart = 5;
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(3);
    Request req = new Request().insertion(insertions).paging(new Paging().size(2).offset(8));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, insertionStart);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertEquals(0, resp.getInsertion().size());
  }

  @Test
  void testResponseInsertionsOnlyHaveKeyFields() throws DeliveryException {
    Map<String, Object> properties = new HashMap<>();
    properties.put("a", 3.3);
    Insertion reqIns = InsertionFactory.createInsertionWithProperties("aaa", properties);
    reqIns.setRetrievalRank(3);
    reqIns.setRetrievalScore((float) 2.2);
    List<Insertion> insertions = new ArrayList<>();
    insertions.add(reqIns);

    Request req = new Request().insertion(insertions).paging(new Paging().size(1).offset(0));
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    Insertion respIns = resp.getInsertion().get(0);

    assertEquals(reqIns.getContentId(), respIns.getContentId());
    assertNull(respIns.getRetrievalRank());
    assertNull(respIns.getRetrievalScore());
    assertNull(respIns.getProperties());
    
    assertNotNull(reqIns.getRetrievalRank());
    assertNotNull(reqIns.getRetrievalScore());
    assertNotNull(reqIns.getProperties());
  }

  @Test
  void testPagingZeroSizeReturnsAll() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request req = new Request().paging(new Paging().offset(0).size(0)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertAllResponseInsertions(insertions, resp);
  }

  @Test
  void testPagingZeroOffset() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request req = new Request().paging(new Paging().offset(0).size(5)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);
    
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
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request req = new Request().paging(new Paging().offset(5).size(5)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);
    
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
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request req = new Request().paging(new Paging().offset(0).size(11)).insertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(req, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(req.getRequestId().length() > 0);
    assertAllResponseInsertions(insertions, resp);
  }

  private void assertAllResponseInsertions(List<Insertion> insertions, Response resp) {
    assertEquals(10, resp.getInsertion().size());
    for (int i = 0; i < 10; i++) {
      Insertion insertion = insertions.get(i);
      assertEquals(i, insertion.getPosition());
      assertTrue(insertion.getInsertionId().length() > 0);
    }
  }
}
