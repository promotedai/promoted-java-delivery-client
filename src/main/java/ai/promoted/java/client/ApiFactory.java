package ai.promoted.java.client;

/**
 * Factory interface for creating API clients.
 */
public interface ApiFactory {
  Delivery createSdkDelivery();
  Delivery createApiDelivery(String endpoint, String apiKey, long timeoutMillis, boolean warmup);
  Metrics createApiMetrics(String endpoint, String apiKey, long timeoutMillis);
}
