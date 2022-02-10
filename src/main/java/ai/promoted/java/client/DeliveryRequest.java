package ai.promoted.java.client;

import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.Request;

public class DeliveryRequest {
  private final Request request;
  private final boolean onlyLog;
  private final InsertionPageType insertionPageType;
  private final CohortMembership experiment;

  public DeliveryRequest(Request request, CohortMembership experiment, boolean onlyLog,
      InsertionPageType insertionPageType) {
    this.request = request;
    this.onlyLog = onlyLog;
    this.experiment = experiment;
    this.insertionPageType = insertionPageType;
  }

  public Request getRequest() {
    return request;
  }

  public boolean isOnlyLog() {
    return onlyLog;
  }

  public InsertionPageType getInsertionPageType() {
    return insertionPageType;
  }

  public CohortMembership getExperiment() {
    return experiment;
  }
}
