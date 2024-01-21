package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.util.List;
import org.junit.jupiter.api.Test;
import ai.promoted.proto.delivery.Insertion;
import ai.promoted.proto.delivery.Paging;
import ai.promoted.proto.delivery.Request;
import ai.promoted.proto.delivery.Response;

class SdkDeliveryTest {

  @Test
  void testInvalidPagingOffsetAndRetrievalInsertionOffset() {
    Request.Builder reqBuilder = Request.newBuilder().setPaging(Paging.newBuilder().setOffset(10).setSize(5).build()).addAllInsertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 100);
    Exception exception = assertThrows(
        DeliveryException.class, 
        () -> new SdkDelivery().runDelivery(dreq));
        
    assertTrue(exception.getMessage().contains("offset should be >= insertion start"));
  }

  @Test
  void testValidPagingOffsetAndRetrievalInsertionOffset() throws DeliveryException {
    Request.Builder reqBuilder = Request.newBuilder().setPaging(Paging.newBuilder().setOffset(10).setSize(5).build()).addAllInsertion(TestUtils.createTestRequestInsertions(10));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 5);
    Response resp = new SdkDelivery().runDelivery(dreq);
    assertNotNull(resp);
  }

  @Test
  void testNoPagingReturnsAll() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertAllResponseInsertions(resp);
  }
  
  @Test
  void testRetrievalInsertionOffsetSetToOffset() throws DeliveryException {
    int retrievalInsertionOffset = 5;
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(3);
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(insertions).setPaging(Paging.newBuilder().setOffset(5).setSize(2));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, retrievalInsertionOffset);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertEquals(2, resp.getInsertionCount());
  }

  @Test
  void testRetrievalInsertionOffsetLessThanOffset() throws DeliveryException {
    int retrievalInsertionOffset = 5;
    int offsetDiff = 1;
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(3);
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(insertions).setPaging(Paging.newBuilder().setOffset(6).setSize(2));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, retrievalInsertionOffset);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertEquals(2, resp.getInsertionCount());

    // Returns positions 1 and 2 since we want an offset one past the request insertion start.
    for (int i = 0; i < resp.getInsertionCount(); i++) {
      Insertion ins = resp.getInsertion(i);
      assertEquals(retrievalInsertionOffset + offsetDiff + i, ins.getPosition());
      assertEquals("" + (i + offsetDiff), ins.getContentId());
    }
  }

  @Test
  void testRetrievalInsertionOffsetWithOffsetOutsideSize() throws DeliveryException {
    int retrievalInsertionOffset = 5;
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(3);
    Request.Builder reqBuilder = Request.newBuilder().addAllInsertion(insertions).setPaging(Paging.newBuilder().setOffset(8).setSize(2));
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, retrievalInsertionOffset);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertEquals(0, resp.getInsertionCount());
  }

  @Test
  void testPagingZeroSizeReturnsAll() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request.Builder reqBuilder = Request.newBuilder().setPaging(Paging.newBuilder().setOffset(0).setSize(0)).addAllInsertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertAllResponseInsertions(resp);
  }

  @Test
  void testPagingZeroOffset() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request.Builder reqBuilder = Request.newBuilder().setPaging(Paging.newBuilder().setOffset(0).setSize(5)).addAllInsertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertEquals(5, resp.getInsertionCount());
    for (int i = 0; i < 5; i++) {
      Insertion insertion = resp.getInsertion(i);
      assertEquals(i, insertion.getPosition());
    }
  }
  
  @Test
  void testPagingNonZeroOffset() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request.Builder reqBuilder = Request.newBuilder().setPaging(Paging.newBuilder().setOffset(5).setSize(5)).addAllInsertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertEquals(5, resp.getInsertionCount());
    for (int i = 5; i < 10; i++) {
      Insertion insertion = resp.getInsertion(i-5);
      assertEquals(i, insertion.getPosition());
    }
  }
  
  @Test
  void testPagingSizeMoreThanInsertions() throws Exception {
    List<Insertion> insertions = TestUtils.createTestRequestInsertions(10);
    Request.Builder reqBuilder = Request.newBuilder().setPaging(Paging.newBuilder().setOffset(0).setSize(11)).addAllInsertion(insertions);
    DeliveryRequest dreq = new DeliveryRequest(reqBuilder, null, false, 0);
    
    Response resp = new SdkDelivery().runDelivery(dreq);
    
    assertTrue(reqBuilder.getRequestId().length() > 0);
    assertTrue(resp.getRequestId().length() > 0);
    assertAllResponseInsertions(resp);
  }

  private void assertAllResponseInsertions(Response resp) {
    assertEquals(10, resp.getInsertionCount());
    for (int i = 0; i < 10; i++) {
      Insertion insertion = resp.getInsertion(i);
      assertEquals(i, insertion.getPosition());
      assertTrue(insertion.getInsertionId().length() > 0);
    }
  }
}
