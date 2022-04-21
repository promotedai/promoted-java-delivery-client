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

  public static final DefaultDeliveryRequestValidator INSTANCE = new DefaultDeliveryRequestValidator();
  
  /**
   * @see DeliveryRequestValidator#validate(DeliveryRequest, boolean)
   */
  public List<String> validate(DeliveryRequest request, boolean isShadowTraffic) {
    List<String> validationErrors = new ArrayList<>();
    
    // Check the ids.
    validationErrors.addAll(validateIds(request.getRequest(), request.getExperiment()));
    
    // Full delivery requires unpaged insertions.
    if (request.getInsertionPageType() == InsertionPageType.PREPAGED) {
      if (!request.isOnlyLog()) {
        validationErrors.add("Delivery expects unpaged insertions");
      } else if (isShadowTraffic) {
        validationErrors.add("Insertions must be unpaged when shadow traffic is on");
      }
    }
    
    return validationErrors;
  }

  private List<String> validateIds(Request request, CohortMembership experiment) {
    List<String> validationErrors = new ArrayList<>();

    if (request == null) {
      validationErrors.add("Request must be set");
    } else {
      if (request.getRequestId() != null) {
        validationErrors.add("Request.requestId should not be set");
      }

      if (request.getUserInfo() == null) {
        validationErrors.add("Request.userInfo should be set");
      } else if (request.getUserInfo().getLogUserId() == null
          || request.getUserInfo().getLogUserId().isBlank())
        validationErrors.add("Request.userInfo.logUserId should be set");
    }

    if (request.getInsertion() == null) {
      validationErrors.add("Request.insertion should be set");
    } else {
      for (Insertion ins : request.getInsertion()) {
        if (ins.getRequestId() != null) {
          validationErrors.add("Insertion.requestId should not be set");
        }
        if (ins.getInsertionId() != null) {
          validationErrors.add("Insertion.insertionId should not be set");
        }
        if (ins.getContentId() == null || ins.getContentId().isBlank()) {
          validationErrors.add("Insertion.contentId should be set");
        }
      }
    }

    if (experiment != null) {
      if (experiment.getPlatformId() != null) {
        validationErrors.add("Experiment.platformId should not be set");
      }
      if (experiment.getUserInfo() != null) {
        validationErrors.add("Experiment.userInfo should not be set");
      }
      if (experiment.getTiming() != null) {
        validationErrors.add("Experiment.timing should not be set");
      }
    }

    return validationErrors;
  }

}
