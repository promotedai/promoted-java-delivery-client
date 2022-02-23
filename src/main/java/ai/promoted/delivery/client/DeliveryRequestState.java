package ai.promoted.delivery.client;

import java.util.logging.Logger;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;

/**
 * Holds state of a request during processing.
 */
class DeliveryRequestState {

  private static final Logger LOGGER = Logger.getLogger(DeliveryRequestState.class.getName());

  /** The delivery request we're processing */
  private final DeliveryRequest deliveryRequest;

  /**
   * Creates a state object for the given request.
   * 
   * @param deliveryRequest the delivery request we are processing.
   */
  public DeliveryRequestState(DeliveryRequest deliveryRequest) {
    this.deliveryRequest = deliveryRequest;
  }
  
  /**
   * Gets the response to return to the client with any necessary post-processing.
   * @param resp the response from Delivery API.
   * @return the post-processed response
   */
  public Response getResponseToReturn(Response resp) {
    return resp;
  }

  /**
   * Gets the request to send to Delivery API with any necessary preprocessing.
   * 
   * @param maxRequestInsertions the maximum number of request insertions, which we will trim to if necessary.
   * @return the pre-processed request.
   */
  public Request getRequestToSend(int maxRequestInsertions) {
    Request req = deliveryRequest.getRequest();
    
    // Trim the list if necessary.
    if (req.getInsertion().size() > maxRequestInsertions) {
      LOGGER.warning("Too many request insertions, truncating at " + maxRequestInsertions);
      req.setInsertion(req.getInsertion().subList(0, maxRequestInsertions));
    }
    
    return req;
  }
}