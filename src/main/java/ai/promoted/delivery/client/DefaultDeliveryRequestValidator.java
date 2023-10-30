package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.Insertion;
import ai.promoted.delivery.model.Request;

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

    Request req = request.getRequest();
    if (req == null) {
      validationErrors.add("Request must be set");
      return validationErrors;
    }

    // Check the ids.
    validationErrors.addAll(validateIds(request.getRequest(), request.getExperiment()));

    // Insertion start should be >= 0.
    if (request.getRetrievalInsertionOffset() < 0) {
      validationErrors.add("Insertion start must be greater or equal to 0");
    }

    return validationErrors;
  }

  private List<String> validateIds(Request request, CohortMembership experiment) {
    List<String> validationErrors = new ArrayList<>();


    if (request.getRequestId() != null) {
      validationErrors.add("Request.requestId should not be set");
    }

    if (request.getUserInfo() == null) {
      validationErrors.add("Request.userInfo should be set");
    } else if (request.getUserInfo().getAnonUserId() == null
        || request.getUserInfo().getAnonUserId().isBlank()) {
      validationErrors.add("Request.userInfo.anonUserId should be set");
    }

    if (request.getInsertion() == null) {
      validationErrors.add("Request.insertion should be set");
    } else {
      for (Insertion ins : request.getInsertion()) {
        if (ins.getInsertionId() != null) {
          validationErrors.add("Insertion.insertionId should not be set");
        }
        if (ins.getContentId() == null || ins.getContentId().isBlank()) {
          validationErrors.add("Insertion.contentId should be set");
        }
      }
    }

    return validationErrors;
  }

}
