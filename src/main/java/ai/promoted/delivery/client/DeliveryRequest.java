package ai.promoted.delivery.client;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Nullable;
import ai.promoted.delivery.model.Request;
import ai.promoted.proto.event.CohortMembership;

/**
 * DeliveryRequest is the input into delivery.
 */
public class DeliveryRequest implements Cloneable {

  /** The underlying request for ranked content. */
  private Request request;

  /** True to only send logs to Metrics API */
  private final boolean onlyLog;

  /** The start index in the request insertions in the list of ALL insertions. */
  private final int retrievalInsertionOffset;

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
   * @param retrievalInsertionOffset start index in the request insertions in the list of ALL insertions
   */
  public DeliveryRequest(Request request, CohortMembership experiment, boolean onlyLog,
      int retrievalInsertionOffset, DeliveryRequestValidator validator) {
    this.request = request;
    this.onlyLog = onlyLog;
    this.experiment = experiment;
    this.retrievalInsertionOffset = retrievalInsertionOffset;
    this.validator = validator;
  }

  /**
   * Instantiates a new delivery request.
   *
   * @param request the request to process
   * @param experiment the experiment that the user is in, may be null, which means apply the
   *        treatment
   * @param onlyLog if true, will log to Promoted.ai Metrics but not call Delivery API to re-rank
   * @param retrievalInsertionOffset start index in the request insertions in the list of ALL insertions
   */
  public DeliveryRequest(Request request, CohortMembership experiment, boolean onlyLog,
      int retrievalInsertionOffset) {
    this(request, experiment, onlyLog, retrievalInsertionOffset, DefaultDeliveryRequestValidator.INSTANCE);
  }

  /**
   * Instantiates a new delivery request.
   *
   * @param request the request to process
   * @param experiment the experiment that the user is in, may be null, which means apply the
   *        treatment
   */
  public DeliveryRequest(Request request, CohortMembership experiment) {
    this(request, experiment, false, 0);
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
   * Gets the insertion start.
   *
   * @return the insertion start
   */
  public int getRetrievalInsertionOffset() {
    return retrievalInsertionOffset;
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
   *
   * @return a list of validation errors, which may be empty.
   */
  public List<String> validate() {
    if (validator != null ) {
      return validator.validate(this);
    }    
    return new ArrayList<>();
  }
}