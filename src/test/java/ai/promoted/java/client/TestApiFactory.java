package ai.promoted.java.client;

/**
 * API factory to configure with mocks/stubs for use in unit tests.
 */
public class TestApiFactory implements ApiFactory {

  private Delivery sdkDelivery;
  private Delivery apiDelivery;
  private Metrics apiMetrics;
  
  public TestApiFactory(Delivery sdkDelivery, Delivery apiDelivery, Metrics apiMetrics) {
    this.sdkDelivery = sdkDelivery;
    this.apiDelivery = apiDelivery;
    this.apiMetrics = apiMetrics;
  }

  @Override
  public Delivery createSdkDelivery() {
    return sdkDelivery;
  }

  @Override
  public Delivery createApiDelivery(String endpoint, String apiKey, long timeoutMillis,
      boolean warmup) {
    return apiDelivery;
  }

  @Override
  public Metrics createApiMetrics(String endpoint, String apiKey, long timeoutMillis) {
    return apiMetrics;
  }
  
  public Delivery getSdkDelivery() {
    return sdkDelivery;
  }

  public Delivery getApiDelivery() {
    return apiDelivery;
  }

  public Metrics getApiMetrics() {
    return apiMetrics;
  }
}
