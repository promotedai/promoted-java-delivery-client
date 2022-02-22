package ai.promoted.delivery.client;

/**
 * API factory that creates the standard API clients.
 */
public class DefaultApiFactory implements ApiFactory {

  @Override
  public Delivery createSdkDelivery() {
    return new SdkDelivery();
  }

  @Override
  public Delivery createApiDelivery(String endpoint, String apiKey, long timeoutMillis, boolean warmup, int maxRequestInsertions) {
    return new ApiDelivery(endpoint, apiKey, timeoutMillis, warmup, maxRequestInsertions);
  }

  @Override
  public Metrics createApiMetrics(String endpoint, String apiKey, long timeoutMillis) {
    return new ApiMetrics(endpoint, apiKey, timeoutMillis);
  }
}
