package ai.promoted.delivery.client;

import java.util.List;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;

/**
 * Holds state of a request during processing.
 */
class DeliveryRequestState {

  /** The delivery request we're processing */
  private final DeliveryRequest deliveryRequest;

  /** Any insertions on the request that were not passed to Delivery API */
  private List<Insertion> nonApiInsertions;
  
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
    
    // Add any insertions back past the maximum, and assign them an insertion id.
    if (nonApiInsertions != null) {
      int nextIndex = 0;
      if (resp.getInsertion().size() > 0) {
        nextIndex = resp.getInsertion().get(resp.getInsertion().size()-1).getPosition() + 1;
      }
      
      for (Insertion ins : nonApiInsertions) {
        InsertionFactory.prepareResponseInsertion(ins, nextIndex);
        resp.addInsertionItem(ins);
        nextIndex++;
      }
    }
    
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
      nonApiInsertions = req.getInsertion().subList(maxRequestInsertions, req.getInsertion().size());
      req.setInsertion(req.getInsertion().subList(0, maxRequestInsertions));
    }
    
    return req;
  }
}