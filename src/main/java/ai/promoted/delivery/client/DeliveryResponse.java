package ai.promoted.delivery.client;

import ai.promoted.delivery.model.Response;
import ai.promoted.proto.delivery.ExecutionServer;

/**
 * Delivery response is the output from delivery.
 */
public class DeliveryResponse {
  
  /** The response from Delivery. */
  private final Response response;
  
  /** The client request id for tracking purposes, auto-generated if not supplied on the request. */
  private final String clientRequestId;
  
  /** The execution server which indicates if delivery happened in the SDK or vai Delivery API */
  private final ExecutionServer executionServer;
  
  /**
   * Instantiates a new delivery response.
   *
   * @param response the response from Delivery
   * @param clientRequestId the client request id
   * @param executionServer the execution server (SDK or API)
   */
  public DeliveryResponse(Response response, String clientRequestId,
      ExecutionServer executionServer) {
    this.response = response;
    this.clientRequestId = clientRequestId;
    this.executionServer = executionServer;
  }

  /**
   * Gets the execution server (SDK or API).
   *
   * @return the execution server
   */
  public ExecutionServer getExecutionServer() {
    return executionServer;
  }

  /**
   * Gets the client request id.
   *
   * @return the client request id
   */
  public String getClientRequestId() {
    return clientRequestId;
  }

  /**
   * Gets the response.
   *
   * @return the response
   */
  public Response getResponse() {
    return response;
  }
}
