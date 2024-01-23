package ai.promoted.delivery.client;

import java.util.Map;
import java.util.UUID;
import ai.promoted.proto.delivery.Insertion;

/**
 * Helper method to create Insertions.
 */
public class InsertionFactory {
  /**
   * Create an insertion builder with a content id and properties.
   * @param contentId the content id
   * @param properties the properties as a map
   * @return a populated Insertion
   */
  public static Insertion.Builder createInsertionBuilderWithProperties(String contentId, Map<String, Object> properties) {
    Insertion.Builder insBuilder = Insertion.newBuilder().setContentId(contentId);
    if (!properties.isEmpty()) {
      insBuilder.setProperties(PropertiesFactory.createPropertiesBuilder(properties));
    }
    return insBuilder;
  }

  /**
   * Gets a response insertion builder ready to return, ensuring position and id are set.
   * @param ins the insertion
   * @param position the position to set
   */
  public static void prepareResponseInsertionBuilder(Insertion.Builder insBuilder, int position) {
    insBuilder.setPosition(position);
    // If the Request Insertion insertionId is set, pass through the insertion ID.
    if (insBuilder.getInsertionId().isBlank()) {
      insBuilder.setInsertionId(UUID.randomUUID().toString());
    }
  }
}
