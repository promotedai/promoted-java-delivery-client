package ai.promoted.delivery.client;

class DeliveryPlan {

  private String clientRequestId;
  private boolean useApiResponse;

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
