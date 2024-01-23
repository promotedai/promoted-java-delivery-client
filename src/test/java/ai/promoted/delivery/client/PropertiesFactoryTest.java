package ai.promoted.delivery.client;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.proto.common.Properties;

class PropertiesFactoryTest {

  @Test
  void testCreateProperties_validPropertyTypes() {
    Map<String, Object> map = new HashMap<>();
    map.put("boolean", true);
    map.put("integer", 5);
    map.put("long", 6L);
    map.put("float", 7F);
    map.put("double", 8D);
    map.put("string", "9");
    map.put("map", Map.of("subfield", 12));
    map.put("list", Arrays.asList(10, 11));
    map.put("null", null);
    Properties properties = PropertiesFactory.createProperties(map);
    assertTrue(properties.hasStruct());
    Map<String, com.google.protobuf.Value> fields = properties.getStruct().getFieldsMap();
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
  void testCreateProperties_invalidPropertyType() {
    Map<String, Object> map = new HashMap<>();
    map.put("object", new Object());
    assertThrows(UnsupportedOperationException.class, () -> {
      PropertiesFactory.createProperties(map);
    });
  }

}
