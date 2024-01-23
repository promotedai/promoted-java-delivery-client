package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.jupiter.api.Test;
import ai.promoted.proto.delivery.Insertion;

class InsertionFactoryTest {

  @Test
  void testCreateInsertionBuilderWithProperties() {
    String contentId = "a";
    Map<String, Object> properties = new HashMap<>();
    properties.put("some", "thing");
    Insertion.Builder insertionBuilder = InsertionFactory.createInsertionBuilderWithProperties(contentId, properties);
    assertEquals(contentId, insertionBuilder.getContentId());
    assertEquals(1, insertionBuilder.getProperties().getStruct().getFieldsCount());
    assertEquals("thing", insertionBuilder.getProperties().getStruct().getFieldsMap().get("some").getStringValue());
  }

  @Test
  void testPrepareResponseInsertionBuilder_insertionIdUnset() {
    Insertion.Builder emptyIns = Insertion.newBuilder();
    InsertionFactory.prepareResponseInsertionBuilder(emptyIns, 6);
    assertNotNull(emptyIns.getInsertionId());
    assertTrue(emptyIns.getInsertionId().length() > 0);
    assertEquals(6, emptyIns.getPosition());
  }

  @Test
  void testPprepareResponseInsertionBuilder_insertionIdSet() {
    Insertion.Builder ins = Insertion.newBuilder();
    ins.setInsertionId("uuid");
    InsertionFactory.prepareResponseInsertionBuilder(ins, 6);
    assertNotNull(ins.getInsertionId());
    assertEquals("uuid", ins.getInsertionId());
    assertEquals(6, ins.getPosition());
  }
}
