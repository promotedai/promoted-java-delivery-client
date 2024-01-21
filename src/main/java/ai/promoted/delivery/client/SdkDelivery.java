package ai.promoted.delivery.client;

import java.util.UUID;
import ai.promoted.proto.delivery.Insertion;
import ai.promoted.proto.delivery.Paging;
import ai.promoted.proto.delivery.Request;
import ai.promoted.proto.delivery.Response;

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
    Request.Builder requestBuilder = deliveryRequest.getRequestBuilder();

    // Set a request id.
    requestBuilder.setRequestId(UUID.randomUUID().toString());

    Paging paging = requestBuilder.getPaging();
    if (!requestBuilder.hasPaging()) {
      paging = Paging.newBuilder().setOffset(0).setSize(requestBuilder.getInsertionCount()).build();
    }

    // Adjust offset and size.
    int offset = Math.max(0, paging.getOffset());
    int index = offset - deliveryRequest.getRetrievalInsertionOffset();
    if (offset < deliveryRequest.getRetrievalInsertionOffset()) {
      throw new DeliveryException("offset should be >= insertion start (specifically, the global position)");
    }

    int size = paging.getSize();
    if (size <= 0) {
      size = requestBuilder.getInsertionCount();
    }

    int finalInsertionSize = Math.min(size, requestBuilder.getInsertionCount() - index);
    Response.Builder respBuilder = Response.newBuilder().setRequestId(requestBuilder.getRequestId());
    for (int i = 0; i < finalInsertionSize; i++) {
      Insertion reqIns = requestBuilder.getInsertion(index);
      Insertion.Builder respInsBuilder = Insertion.newBuilder().setContentId(reqIns.getContentId())
          .setInsertionId(reqIns.getInsertionId());
      InsertionFactory.prepareResponseInsertion(respInsBuilder, offset);
      respBuilder.addInsertion(respInsBuilder);
      index++;
      offset++;
    }
    return respBuilder.build();
  }
}
