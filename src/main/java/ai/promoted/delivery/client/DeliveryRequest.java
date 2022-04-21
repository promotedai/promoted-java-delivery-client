package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.Request;

/**
 * DeliveryRequest is the input into delivery.
 */
public class DeliveryRequest implements Cloneable {

  /** The underlying request for ranked content. */
  private Request request;

  /** True to only send logs to Metrics API */
  private final boolean onlyLog;

  /** The insertion page type (PREPAGED or UNPAGED). */
  private final InsertionPageType insertionPageType;

  /** An experiment (if any) to use for this request. */
  @Nullable
  private final CohortMembership experiment;

  /** The request validator. */
  private final DeliveryRequestValidator validator;
  
  /**
   * Instantiates a new delivery request.
   *
   * @param request the request to process
   * @param experiment the experiment that the user is in, may be null, which means apply the
   *        treatment
   * @param onlyLog if true, will log to Promoted.ai Metrics but not call Delivery API to re-rank
   * @param insertionPageType the insertion page type, should be UNPAGED in order for Promoted.ai to
   *        best rank the results
   */
  public DeliveryRequest(Request request, CohortMembership experiment, boolean onlyLog,
      InsertionPageType insertionPageType, DeliveryRequestValidator validator) {
    this.request = request;
    this.onlyLog = onlyLog;
    this.experiment = experiment;
    this.insertionPageType = insertionPageType;
    this.validator = validator;
  }

  /**
   * Instantiates a new delivery request.
   *
   * @param request the request to process
   * @param experiment the experiment that the user is in, may be null, which means apply the
   *        treatment
   * @param onlyLog if true, will log to Promoted.ai Metrics but not call Delivery API to re-rank
   * @param insertionPageType the insertion page type, should be UNPAGED in order for Promoted.ai to
   *        best rank the results
   */
  public DeliveryRequest(Request request, CohortMembership experiment, boolean onlyLog,
      InsertionPageType insertionPageType) {
    this(request, experiment, onlyLog, insertionPageType, DefaultDeliveryRequestValidator.INSTANCE);
  }

  /**
   * Instantiates a new delivery request.
   *
   * @param request the request to process
   * @param experiment the experiment that the user is in, may be null, which means apply the
   *        treatment
   */
  public DeliveryRequest(Request request, CohortMembership experiment) {
    this(request, experiment, false, InsertionPageType.UNPAGED);
  }

  /**
   * Instantiates a new delivery request.
   *
   * @param request the request to process =
   */
  public DeliveryRequest(Request request) {
    this(request, null);
  }

  /**
   * Gets the underlying request for ranked content.
   *
   * @return the request
   */
  public Request getRequest() {
    return request;
  }

  /**
   * Checks whether or not to only perform logging and not call Delivery API.
   *
   * @return true, if is only log
   */
  public boolean isOnlyLog() {
    return onlyLog;
  }

  /**
   * Gets the insertion page type.
   *
   * @return the insertion page type
   */
  public InsertionPageType getInsertionPageType() {
    return insertionPageType;
  }

  /**
   * Gets the experiment to use for this request, if any.
   *
   * @return the experiment
   */
  public CohortMembership getExperiment() {
    return experiment;
  }

  public DeliveryRequest clone() throws CloneNotSupportedException {
    DeliveryRequest deliveryRequestCopy = (DeliveryRequest) super.clone();
    deliveryRequestCopy.request = request.clone();
    return deliveryRequestCopy;
  }

  /**
   * Checks the state of this delivery requests and collects/returns any validation errors.
   * @param isShadowTraffic 
   * 
   * @return a list of validation errors, which may be empty.
   */
  public List<String> validate(boolean isShadowTraffic) {
    if (validator != null ) {
      return validator.validate(this, isShadowTraffic);
    }    
    return new ArrayList<>();
  }
}