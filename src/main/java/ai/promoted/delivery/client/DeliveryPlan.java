package ai.promoted.delivery.client;

/**
 * A plan object that can indicate how to execute delivery.
 */
class DeliveryPlan {

  private String clientRequestId;
  private boolean useApiResponse;

  /**
   * @param clientRequestId The client's requestId.  Important because the ID should match
   *                        if both API and SDK Delivery are done.
   * @param useApiResponse Whether the SDK should attempt to use Delivery API's Response.
   */
  public DeliveryPlan(String clientRequestId, boolean useApiResponse) {
    this.clientRequestId = clientRequestId;
    this.useApiResponse = useApiResponse;
  }

  public String getClientRequestId() {
    return clientRequestId;
  }

  public boolean useApiResponse() {
    return useApiResponse;
  }
}
