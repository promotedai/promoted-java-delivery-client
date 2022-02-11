package ai.promoted.java.client;

import java.util.Map;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Properties;

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
    Properties props = new Properties().structField(properties);
    return new Insertion().contentId(contentId).properties(props);
  }
}
