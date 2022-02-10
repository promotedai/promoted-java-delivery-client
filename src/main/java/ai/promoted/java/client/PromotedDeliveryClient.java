package ai.promoted.java.client;

import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Logger;
import ai.promoted.delivery.model.ClientInfo;
import ai.promoted.delivery.model.ClientType;
import ai.promoted.delivery.model.CohortArm;
import ai.promoted.delivery.model.CohortMembership;
import ai.promoted.delivery.model.DeliveryExecution;
import ai.promoted.delivery.model.DeliveryLog;
import ai.promoted.delivery.model.ExecutionServer;
import ai.promoted.delivery.model.LogRequest;
import ai.promoted.delivery.model.Request;
import ai.promoted.delivery.model.Response;
import ai.promoted.delivery.model.Timing;
import ai.promoted.delivery.model.TrafficType;

/**
 * PromotedDeliveryClient is the main class for interacting with the Promoted.ai Delivery API.
 */
public class PromotedDeliveryClient {

  private static final String SERVER_VERSION = "java.1.0.1";

  private static final Logger LOGGER = Logger.getLogger(PromotedDeliveryClient.class.getName());

  /** Default timeout for delivery calls. */
  public static final long DEFAULT_DELIVERY_TIMEOUT_MILLIS = 250;

  /** Default timeout for metrics calls. */
  public static final long DEFAULT_METRICS_TIMEOUT_MILLIS = 3000;

  /** Executor to run metrics logging in the background. */
  public static final int DEFAULT_METRICS_THREAD_POOL_SIZE = 5;

  /** Service for SDK-side delivery, used for fallbacks, experiment controls, and only-log mode. */
  private final SdkDelivery sdkDelivery;

  /** Service for API-side delivery. */
  private final ApiDelivery apiDelivery;

  /** Service for metrics logging. */
  private final ApiMetrics apiMetrics;

  /** Executor to send metrics requests in the background. */
  private final Executor metricsExecutor;

  /** Optional function to see if treatment should be applied to a cohort membership. */
  private final ApplyTreatmentChecker applyTreatmentChecker;

  /**
   * Instantiates a new promoted delivery client.
   *
   * @param deliveryEndpoint the delivery endpoint
   * @param deliveryApiKey the delivery api key
   * @param deliveryTimeoutMillis the delivery timeout millis
   * @param metricsEndpoint the metrics endpoint
   * @param metricsApiKey the metrics api key
   * @param metricsTimeoutMillis the metrics timeout millis
   * @param warmup the warmup
   * @param metricsExecutor the metrics executor
   * @param applyTreatmentChecker the apply treatment checker
   */
  private PromotedDeliveryClient(String deliveryEndpoint, String deliveryApiKey,
      long deliveryTimeoutMillis, String metricsEndpoint, String metricsApiKey,
      long metricsTimeoutMillis, boolean warmup, Executor metricsExecutor,
      ApplyTreatmentChecker applyTreatmentChecker) {

    if (deliveryTimeoutMillis <= 0) {
      deliveryTimeoutMillis = DEFAULT_DELIVERY_TIMEOUT_MILLIS;
    }
    if (metricsTimeoutMillis <= 0) {
      metricsTimeoutMillis = DEFAULT_METRICS_TIMEOUT_MILLIS;
    }

    if (metricsExecutor == null) {
      metricsExecutor = Executors.newSingleThreadExecutor();
    }
    this.metricsExecutor = metricsExecutor;
    this.applyTreatmentChecker = applyTreatmentChecker;
    this.sdkDelivery = new SdkDelivery();
    this.apiMetrics = new ApiMetrics(metricsEndpoint, metricsApiKey, metricsTimeoutMillis);
    this.apiDelivery = new ApiDelivery(deliveryEndpoint, deliveryApiKey, deliveryTimeoutMillis);
    if (warmup) {
      apiDelivery.DoWarmup();
    }
  }

  /**
   * Used to call Delivery API. Takes the given list of Content and ranks it.
   *
   * @param deliveryRequest the delivery request
   * @throws DeliveryException when any exception occurs
   */
  public void Deliver(DeliveryRequest deliveryRequest) throws DeliveryException {

    Response deliveryResponse;

    Request request = deliveryRequest.getRequest();

    fillInRequestFields(request);

    CohortMembership cohortMembership = checkCohortMembership(deliveryRequest);

    ExecutionServer execSrv = ExecutionServer.SDK;

    if (deliveryRequest.isOnlyLog() || !shouldApplyTreatment(cohortMembership)) {
      deliveryResponse = sdkDelivery.DoDelivery(deliveryRequest);
    } else {
      try {
        deliveryResponse = apiDelivery.DoDelivery(deliveryRequest);
        execSrv = ExecutionServer.API;
      } catch (DeliveryException ex) {
        LOGGER.warning("Error calling Delivery API, falling back: " + ex);
        deliveryResponse = sdkDelivery.DoDelivery(deliveryRequest);
      }
    }

    // If delivery happened client-side, log the insertions to metrics API.
    if (execSrv != ExecutionServer.API) {
      logToMetrics(deliveryRequest, deliveryResponse, cohortMembership, execSrv);
    }

    System.out.println(deliveryResponse);
  }

  /**
   * Checks to see if we should apply treatment (e.g. calling Delivery API) for the cohort
   * membership.
   *
   * @param cohortMembership the cohort membership to check
   * @return true if Delivery API should be called (the treatment case), false otherwise.
   */
  private boolean shouldApplyTreatment(CohortMembership cohortMembership) {
    if (applyTreatmentChecker != null) {
      return applyTreatmentChecker.ShouldApplyTreatment(cohortMembership);
    }
    if (cohortMembership == null) {
      return false;
    }

    return cohortMembership.getArm() == null || cohortMembership.getArm() != CohortArm.CONTROL;
  }

  /**
   * Creates a cohort membership from the request, based on the provided experiment If there isn't
   * one, returns null.
   *
   * @param deliveryRequest the delivery request
   * @return the cohort membership to use, or null if none
   */
  private CohortMembership checkCohortMembership(DeliveryRequest deliveryRequest) {
    CohortMembership experiment = deliveryRequest.getExperiment();
    if (experiment == null) {
      return null;
    }

    CohortMembership cohortMembership = new CohortMembership().arm(experiment.getArm())
        .cohortId(experiment.getCohortId()).platformId(deliveryRequest.getRequest().getPlatformId())
        .userInfo(deliveryRequest.getRequest().getUserInfo())
        .timing(deliveryRequest.getRequest().getTiming());
    return cohortMembership;
  }

  /**
   * Log to metrics.
   *
   * @param deliveryRequest the delivery request
   * @param deliveryResponse the delivery response
   * @param cohortMembership the cohort membership
   * @param execSrv the execution server (SDK or API) that populated the response
   */
  private void logToMetrics(final DeliveryRequest deliveryRequest, final Response deliveryResponse,
      CohortMembership cohortMembership, final ExecutionServer execSrv) {
    metricsExecutor.execute(() -> {
      LogRequest logRequest =
          createLogRequest(deliveryRequest, deliveryResponse, cohortMembership, execSrv);
      try {
        apiMetrics.DoMetricsLogging(logRequest);
      } catch (DeliveryException ex) {
        LOGGER.warning("Error calling Metrics API: " + ex);
      }
    });
  }

  /**
   * Creates the log request from a delivery request/response.
   *
   * @param deliveryRequest the delivery request
   * @param response the delivery response
   * @param cohortMembershipToLog the cohort membership to log
   * @param execSvr the execution server
   * @return the log request to pass to metrics logging
   */
  private LogRequest createLogRequest(DeliveryRequest deliveryRequest, Response response,
      CohortMembership cohortMembershipToLog, ExecutionServer execSvr) {
    DeliveryLog deliveryLog = new DeliveryLog()
        .execution(new DeliveryExecution().executionServer(execSvr).serverVersion(SERVER_VERSION));
    deliveryLog.setResponse(response);

    // TODO: What fields do we really need to strip from the original request?
    deliveryLog.setRequest(deliveryRequest.getRequest());

    // TODO: What fields must be copied up from Request to LogRequest?
    LogRequest logRequest = new LogRequest().addDeliveryLogItem(deliveryLog);

    if (cohortMembershipToLog != null) {
      logRequest.addCohortMembershipItem(cohortMembershipToLog);
    }

    return logRequest;
  }

  /**
   * Fill in required fields on the request that are typically handled by the SDK.
   *
   * @param request the request to populate
   */
  private void fillInRequestFields(Request request) {
    if (request.getClientInfo() == null) {
      request.setClientInfo(new ClientInfo());
    }
    request.getClientInfo().setClientType(ClientType.SERVER.getValue());
    request.getClientInfo().setTrafficType(TrafficType.PRODUCTION.getValue());

    // If there is no client request id set by the caller, we fill one in.
    ensureClientRequestId(request);

    // If there is no client timestamp set by the caller, we fill in the current time.
    ensureClientTimestamp(request);
  }

  /**
   * Ensure client timestamp is set on the request.
   *
   * @param request the request
   */
  private void ensureClientTimestamp(Request request) {
    if (request.getTiming() == null) {
      request.setTiming(new Timing());
    }
    if (request.getTiming().getClientLogTimestamp() == null) {
      request.getTiming().setClientLogTimestamp(System.currentTimeMillis());
    }
  }

  /**
   * Ensure client request id is set on the request.
   *
   * @param request the request
   */
  private void ensureClientRequestId(Request request) {
    if (request.getClientRequestId() == null || request.getClientRequestId().isBlank()) {
      request.setClientRequestId(UUID.randomUUID().toString());
    }
  }

  /**
   * Builder access for {@link PromotedDeliveryClient}
   *
   * @return the builder
   */
  public static Builder builder() {
    return new Builder();
  }

  /**
   * Builds a {@link PromotedDeliveryClient}.
   */
  public static class Builder {

    /** The delivery endpoint. */
    private String deliveryEndpoint;

    /** The delivery api key. */
    private String deliveryApiKey;

    /** The delivery timeout millis. */
    private long deliveryTimeoutMillis;

    /** The metrics endpoint. */
    private String metricsEndpoint;

    /** The metrics api key. */
    private String metricsApiKey;

    /** The metrics timeout millis. */
    private long metricsTimeoutMillis;

    /** The warmup. */
    private boolean warmup;

    /** The metrics executor. */
    private Executor metricsExecutor;

    /** The apply treatment checker. */
    private ApplyTreatmentChecker applyTreatmentChecker;

    /**
     * Instantiates a new builder.
     */
    private Builder() {}

    /**
     * Sets delivery endpoint.
     *
     * @param deliveryEndpoint the delivery endpoint
     * @return the builder
     */
    public Builder withDeliveryEndpoint(String deliveryEndpoint) {
      this.deliveryEndpoint = deliveryEndpoint;
      return this;
    }

    /**
     * Sets delivery api key.
     *
     * @param deliveryApiKey the delivery api key
     * @return the builder
     */
    public Builder withDeliveryApiKey(String deliveryApiKey) {
      this.deliveryApiKey = deliveryApiKey;
      return this;
    }

    /**
     * Sets delivery timeout millis.
     *
     * @param deliveryTimeoutMillis the delivery timeout millis
     * @return the builder
     */
    public Builder withDeliveryTimeoutMillis(long deliveryTimeoutMillis) {
      this.deliveryTimeoutMillis = deliveryTimeoutMillis;
      return this;
    }

    /**
     * Sets metrics endpoint.
     *
     * @param metricsEndpoint the metrics endpoint
     * @return the builder
     */
    public Builder withMetricsEndpoint(String metricsEndpoint) {
      this.deliveryEndpoint = metricsEndpoint;
      return this;
    }

    /**
     * Sets metrics aoi key.
     *
     * @param metricsApiKey the metrics api key
     * @return the builder
     */
    public Builder withMetricsAoiKey(String metricsApiKey) {
      this.deliveryApiKey = metricsApiKey;
      return this;
    }

    /**
     * Sets metrics timeout millis.
     *
     * @param metricsTimoutMillis the metrics timout millis
     * @return the builder
     */
    public Builder withMetricsTimeoutMillis(long metricsTimoutMillis) {
      this.deliveryTimeoutMillis = metricsTimoutMillis;
      return this;
    }

    /**
     * Sets warmup, which will optionally prime the HTTPClient connection pool.
     *
     * @param warmup the warmup
     * @return the builder
     */
    public Builder withWarmup(boolean warmup) {
      this.warmup = warmup;
      return this;
    }

    /**
     * Sets metrics executor.
     *
     * @param metricsExecutor the metrics executor
     * @return the builder
     */
    public Builder withMetricsExecutor(Executor metricsExecutor) {
      this.metricsExecutor = metricsExecutor;
      return this;
    }

    /**
     * Sets apply treatment checker.
     *
     * @param applyTreatmentChecker the apply treatment checker
     * @return the builder
     */
    public Builder withApplyTreatmentChecker(ApplyTreatmentChecker applyTreatmentChecker) {
      this.applyTreatmentChecker = applyTreatmentChecker;
      return this;
    }

    /**
     * Builds the {@link PromotedDeliveryClient}.
     *
     * @return the promoted delivery client
     */
    public PromotedDeliveryClient build() {
      return new PromotedDeliveryClient(deliveryEndpoint, deliveryApiKey, deliveryTimeoutMillis,
          metricsEndpoint, metricsApiKey, metricsTimeoutMillis, warmup, metricsExecutor,
          applyTreatmentChecker);
    }
  }
}
