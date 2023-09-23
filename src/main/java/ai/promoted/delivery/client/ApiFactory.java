package ai.promoted.delivery.client;

/**
 * Factory interface for creating API clients.
 */
public interface ApiFactory {
  Delivery createSdkDelivery();
  Delivery createApiDelivery(String endpoint, String apiKey, long timeoutMillis, boolean warmup, int maxRequestInsertions, boolean acceptGzip);
  Metrics createApiMetrics(String endpoint, String apiKey, long timeoutMillis);
}
