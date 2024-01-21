package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;
import ai.promoted.proto.delivery.Insertion;
import ai.promoted.proto.delivery.Paging;

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
      paging = Paging.newBuilder().setOffset(0).setSize(request.getInsertion().size()).build();
    }

    // Adjust offset and size.
    int offset = Math.max(0, paging.getOffset());
    int index = offset - deliveryRequest.getRetrievalInsertionOffset();
    if (offset < deliveryRequest.getRetrievalInsertionOffset()) {
      throw new DeliveryException("offset should be >= insertion start (specifically, the global position)");
    }

    int size = paging.getSize();
    if (size <= 0) {
      size = request.getInsertion().size();
    }

    int finalInsertionSize = Math.min(size, request.getInsertion().size() - index);
    List<Insertion> insertionPage = new ArrayList<>(finalInsertionSize);
    for (int i = 0; i < finalInsertionSize; i++) {
      Insertion reqIns = request.getInsertion().get(index);
      Insertion.Builder respInsBuilder = Insertion.newBuilder().setContentId(reqIns.getContentId()).setInsertionId(reqIns.getInsertionId());
      InsertionFactory.prepareResponseInsertion(respInsBuilder, offset);
      insertionPage.add(respInsBuilder.build());
      index++;
      offset++;
    }

    Response response = new Response();
    response.setRequestId(request.getRequestId());
    response.insertion(insertionPage);
    return response;
  }
}
