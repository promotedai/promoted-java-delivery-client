package ai.promoted.delivery.client;

import ai.promoted.proto.delivery.ExecutionServer;
import ai.promoted.proto.delivery.Response;

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
  
  /** If an exception was encountered.  Nullable. */
  private final DeliveryException exception;

  /**
   * Instantiates a new delivery response.
   *
   * @param response the response from Delivery
   * @param clientRequestId the client request id
   * @param executionServer the execution server (SDK or API)
   * @param exception if an exception was encountered
   */
  public DeliveryResponse(Response response, String clientRequestId,
      ExecutionServer executionServer, DeliveryException exception) {
    this.response = response;
    this.clientRequestId = clientRequestId;
    this.executionServer = executionServer;
    this.exception = exception;
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

  /**
   * Gets an exception.
   * 
   * @return the exception
   */
  public DeliveryException getException() {
    return exception;
  }
}
