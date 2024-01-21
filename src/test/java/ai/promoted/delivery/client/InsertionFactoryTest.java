package ai.promoted.delivery.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.proto.delivery.Insertion;

class InsertionFactoryTest {

  @Test
  void testCreateInsertionWithProperties_validPropertyTypes() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("boolean", true);
    properties.put("integer", 5);
    properties.put("long", 6L);
    properties.put("float", 7F);
    properties.put("double", 8D);
    properties.put("string", "9");
    properties.put("map", Map.of("subfield", 12));
    properties.put("list", Arrays.asList(10, 11));
    properties.put("null", null);
    Insertion insertion = InsertionFactory.createInsertionWithProperties("a", properties);
    assertNotNull(insertion.getProperties());
    assertTrue(insertion.getProperties().hasStruct());
    Map<String, com.google.protobuf.Value> fields = insertion.getProperties().getStruct().getFieldsMap();
    assertEquals(9, fields.size());
    assertEquals(true, fields.get("boolean").getBoolValue());
    assertEquals(5, fields.get("integer").getNumberValue());
    assertEquals(6, fields.get("long").getNumberValue());
    assertEquals(7, fields.get("float").getNumberValue());
    assertEquals(8, fields.get("double").getNumberValue());
    assertEquals("9", fields.get("string").getStringValue());
    Map<String, com.google.protobuf.Value> subFields = fields.get("map").getStructValue().getFieldsMap();
    assertEquals(1, subFields.size());
    assertEquals(12, subFields.get("subfield").getNumberValue());
    com.google.protobuf.ListValue list = fields.get("list").getListValue();
    assertEquals(2, list.getValuesCount());
    assertEquals(10, list.getValues(0).getNumberValue());
    assertEquals(11, list.getValues(1).getNumberValue());
    assertTrue(fields.get("null").hasNullValue());
  }

  @Test
  void testCreateInsertionWithProperties_invalidPropertyType() {
    Map<String, Object> properties = new HashMap<>();
    properties.put("object", new Object());
    assertThrows(UnsupportedOperationException.class, () -> {
      InsertionFactory.createInsertionWithProperties("a", properties);

    });
  }

  @Test
  void testPrepareResponseInsertion_insertionIdUnset() {
    Insertion.Builder emptyIns = Insertion.newBuilder();
    InsertionFactory.prepareResponseInsertion(emptyIns, 6);
    assertNotNull(emptyIns.getInsertionId());
    assertTrue(emptyIns.getInsertionId().length() > 0);
    assertEquals(6, emptyIns.getPosition());
  }

  @Test
  void testPrepareResponseInsertion_insertionIdSet() {
    Insertion.Builder ins = Insertion.newBuilder();
    ins.setInsertionId("uuid");
    InsertionFactory.prepareResponseInsertion(ins, 6);
    assertNotNull(ins.getInsertionId());
    assertEquals("uuid", ins.getInsertionId());
    assertEquals(6, ins.getPosition());
  }
}
