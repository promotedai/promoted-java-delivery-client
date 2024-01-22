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
  public Delivery createApiDelivery(String endpoint, String apiKey, long timeoutMillis, boolean warmup, int maxRequestInsertions, boolean acceptGzip, boolean useGrpc) {
    return new ApiDelivery(endpoint, apiKey, timeoutMillis, warmup, maxRequestInsertions, acceptGzip, useGrpc);
  }

  @Override
  public Metrics createApiMetrics(String endpoint, String apiKey, long timeoutMillis) {
    return new ApiMetrics(endpoint, apiKey, timeoutMillis);
  }
}
