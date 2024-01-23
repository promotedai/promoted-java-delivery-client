package ai.promoted.delivery.client;

import java.util.Map;
import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;

import ai.promoted.proto.common.Properties;

/**
 * Helper methods to create Properties.
 */
public class PropertiesFactory {
  /**
   * Create a properties builder with a struct equivalent to the given map.
   * @param map the properties as a map
   * @return a populated Insertion
   */
  public static Properties.Builder createPropertiesBuilder(Map<String, Object> properties) {
    Properties.Builder propertiesBuilder = Properties.newBuilder();
    if (!properties.isEmpty()) {
        propertiesBuilder.setStruct(convertMapToStruct(properties));
    }
    return propertiesBuilder;
  }

  /**
   * Create a properties with a struct equivalent to the given map.
   * @param map the properties as a map
   * @return a populated Insertion
   */
  public static Properties createProperties(Map<String, Object> properties) {
    return createPropertiesBuilder(properties).build();
  }

  private static com.google.protobuf.Struct convertMapToStruct(Map<String, Object> map) {
    com.google.protobuf.Struct.Builder structBuilder = com.google.protobuf.Struct.newBuilder();
    map.forEach((key, value) -> {
      com.google.protobuf.Value protoValue = convertObjectToValue(value);
      structBuilder.putFields(key, protoValue);
    });
    return structBuilder.build();
  }

  private static com.google.protobuf.Value convertObjectToValue(Object object) {
    if (object instanceof Boolean) {
      return com.google.protobuf.util.Values.of((Boolean) object);
    } else if (object instanceof Integer) {
      return com.google.protobuf.util.Values.of((Integer) object);
    } else if (object instanceof Long) {
      return com.google.protobuf.util.Values.of((Long) object);
    } else if (object instanceof Float) {
      return com.google.protobuf.util.Values.of((Float) object);
    } else if (object instanceof Double) {
      return com.google.protobuf.util.Values.of((Double) object);
    } else if (object instanceof String) {
      return com.google.protobuf.util.Values.of((String) object);
    } else if (object instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, Object> map = (Map<String, Object>) object;
      return com.google.protobuf.util.Values.of(convertMapToStruct(map));
    } else if (object instanceof Iterable) {
      Iterable<?> iterable = (Iterable<?>) object;
      ListValue.Builder listBuilder = ListValue.newBuilder();
      for (Object item : iterable) {
        listBuilder.addValues(convertObjectToValue(item));
      }
      return com.google.protobuf.util.Values.of(listBuilder.build());
    } else if (object == null) {
      return com.google.protobuf.Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();
    }
    throw new UnsupportedOperationException("property value class=" + object.getClass());
  }
}
