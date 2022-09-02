package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Paging;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;

/**
 * Implements SDK-side delivery, which does not call Promoted.ai but rather applies paging paramters
 * to the request insertions and sets up for logging. This is useful for experiment controls, error
 * fallbacks, and logging during the integration period.
 */
public class SdkDelivery implements Delivery {

  /**
   * Do delivery.
   *
   * @param deliveryRequest the delivery request
   * @return the populated response
   */
  @Override
  public Response runDelivery(DeliveryRequest deliveryRequest) throws DeliveryException {
    Request request = deliveryRequest.getRequest();

    // Set a request id.
    request.setRequestId(UUID.randomUUID().toString());

    Paging paging = request.getPaging();
    if (paging == null) {
      paging = new Paging().offset(0).size(request.getInsertion().size());
    }

    // Adjust offset and size.
    int offset = paging.getOffset() != null ? Math.max(0, paging.getOffset()) : 0;
    int index = offset - deliveryRequest.getInsertionStart();
    if (offset < deliveryRequest.getInsertionStart()) {
      throw new DeliveryException("offset should be >= insertion start (specifically, the global position)");
    }

    int size = paging.getSize() != null ? paging.getSize() : 0;
    if (size <= 0) {
      size = request.getInsertion().size();
    }

    int finalInsertionSize = Math.min(size, request.getInsertion().size() - index);
    List<Insertion> insertionPage = new ArrayList<>(finalInsertionSize);
    for (int i = 0; i < finalInsertionSize; i++) {
      Insertion reqIns = request.getInsertion().get(index);
      Insertion respIns = new Insertion().contentId(reqIns.getContentId());
      InsertionFactory.prepareResponseInsertion(respIns, offset);
      insertionPage.add(respIns);
      index++;
      offset++;
    }

    return new Response().insertion(insertionPage);
  }
}
