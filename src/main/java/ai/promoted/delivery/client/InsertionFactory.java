package ai.promoted.delivery.client;

import java.util.Map;
import java.util.UUID;
import com.google.protobuf.ListValue;
import com.google.protobuf.NullValue;

import ai.promoted.delivery.model.Insertion;
import ai.promoted.proto.common.Properties;

/**
 * Helper method to create Insertions.
 */
public class InsertionFactory {
  /**
   * Create an insertion with a content id and properties.
   * @param contentId the content id
   * @param properties the properties as a map
   * @return a populated Insertion
   */
  public static Insertion createInsertionWithProperties(String contentId, Map<String, Object> properties) {
    Insertion ins = new Insertion().contentId(contentId);
    if (!properties.isEmpty()) {
      ins.properties(Properties.newBuilder().setStruct(convertMapToStruct(properties)).build());
    }
    return ins;
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
    } else if (object instanceof Iterable) {
      Iterable<?> iterable = (Iterable<?>) object;
      ListValue.Builder listBuilder = ListValue.newBuilder();
      for (Object item : iterable) {
        listBuilder.addValues(convertObjectToValue(item));
      }
      return com.google.protobuf.util.Values.of(listBuilder.build());
    } else if (object instanceof Map) {
      @SuppressWarnings("unchecked")
      Map<String, Object> map = (Map<String, Object>) object;
      return com.google.protobuf.util.Values.of(convertMapToStruct(map));
    }
    return com.google.protobuf.Value.newBuilder().setNullValue(NullValue.NULL_VALUE).build();
  }

  /**
   * Gets a response insertion ready to return, ensuring position and id are set.
   * @param ins the insertion
   * @param position the position to set
   */
  public static void prepareResponseInsertion(Insertion ins, int position) {
    ins.setPosition(position);
    // If the Request Insertion insertionId is set, pass through the insertion ID.
    if (ins.getInsertionId() == null || ins.getInsertionId().isEmpty()) {
      ins.setInsertionId(UUID.randomUUID().toString());
    }
  }
}
