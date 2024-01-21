package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import ai.promoted.proto.delivery.Request;
import ai.promoted.proto.delivery.Insertion;
import ai.promoted.proto.event.CohortMembership;

/**
 * Implements the default delivery request validation logic.
 */
public class DefaultDeliveryRequestValidator implements DeliveryRequestValidator {

  public static final DefaultDeliveryRequestValidator INSTANCE =
      new DefaultDeliveryRequestValidator();

  /**
   * @see DeliveryRequestValidator#validate(DeliveryRequest)
   */
  public List<String> validate(DeliveryRequest request) {
    List<String> validationErrors = new ArrayList<>();

    Request.Builder reqBuilder = request.getRequestBuilder();
    if (reqBuilder == null) {
      validationErrors.add("Request builder must be set");
      return validationErrors;
    }

    // Check the ids.
    validationErrors.addAll(validateIds(request.getRequestBuilder(), request.getExperiment()));

    // Insertion start should be >= 0.
    if (request.getRetrievalInsertionOffset() < 0) {
      validationErrors.add("Insertion start must be greater or equal to 0");
    }

    return validationErrors;
  }

  private List<String> validateIds(Request.Builder reqBuilder, CohortMembership experiment) {
    List<String> validationErrors = new ArrayList<>();


    if (!reqBuilder.getRequestId().isBlank()) {
      validationErrors.add("Request.requestId should not be set");
    }

    if (!reqBuilder.hasUserInfo()) {
      validationErrors.add("Request.userInfo should be set");
    } else if (reqBuilder.getUserInfo().getAnonUserId().isBlank()) {
      validationErrors.add("Request.userInfo.anonUserId should be set");
    }

    for (Insertion ins : reqBuilder.getInsertionList()) {
      if (ins.getContentId().isBlank()) {
        validationErrors.add("Insertion.contentId should be set");
      }
    }

    return validationErrors;
  }

}
