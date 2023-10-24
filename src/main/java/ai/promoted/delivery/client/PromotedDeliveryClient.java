package ai.promoted.delivery.client;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Level;
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
import javax.annotation.Nullable;

/**
 * PromotedDeliveryClient is the main class for interacting with the Promoted.ai Delivery API.
 */
public class PromotedDeliveryClient {

  private static final String SERVER_VERSION = "java.2.0.6";

  private static final Logger LOGGER = Logger.getLogger(PromotedDeliveryClient.class.getName());

  /** Default timeout for delivery calls. */
  public static final long DEFAULT_DELIVERY_TIMEOUT_MILLIS = 250;

  /** Default timeout for metrics calls. */
  public static final long DEFAULT_METRICS_TIMEOUT_MILLIS = 3000;

  /** Executor to run metrics logging in the background. */
  public static final int DEFAULT_METRICS_THREAD_POOL_SIZE = 5;

  /** Default number of maximum request insertion passed to Delivery API. */
  public static final int DEFAULT_MAX_REQUEST_INSERTIONS = 1000;
  
  /** Service for SDK-side delivery, used for fallbacks, experiment controls, and only-log mode. */
  private final Delivery sdkDelivery;

  /** Service for API-side delivery. */
  private final Delivery apiDelivery;

  /** Service for metrics logging. */
  private final Metrics apiMetrics;

  /** Executor to send requests in the background. */
  private final Executor executor;

  /** Optional function to see if treatment should be applied to a cohort membership. */
  private final ApplyTreatmentChecker applyTreatmentChecker;
  
  /** Percent of traffic not already sent to delivery to send as shadow traffic in the range [0, 1]. */
  private final float shadowTrafficDeliveryRate;
  
  /** For sampling use cases. */
  private final Sampler sampler;
  
  /** Whether to do extra validation and log before sending a delivery request. */
  private boolean performChecks;
  
  /** Flag that indicates whether shadow traffic is blocking (vs background) call. */
  private boolean blockingShadowTraffic;

  /**
   * Instantiates a new promoted delivery client.
   * This class is thread-safe and intended to be used as a singleton.
   *
   * @param deliveryEndpoint the delivery endpoint
   * @param deliveryApiKey the delivery api key
   * @param deliveryTimeoutMillis the delivery timeout millis
   * @param metricsEndpoint the metrics endpoint
   * @param metricsApiKey the metrics api key
   * @param metricsTimeoutMillis the metrics timeout millis
   * @param warmup the warmup
   * @param executor the executor for sending async requests to Promoted.
   * @param maxRequestInsertions the maximum number of request insertions sent to Delivery API
   * @param applyTreatmentChecker the apply treatment checker
   * @param apiFactory for creating API clients, may be null to use the built-in defaults
   * @param shadowTrafficDeliveryRate rate = [0,1] of traffic not otherwise sent to Delivery API sent as shadow traffic
   * @param sampler the sampler to use
   * @param blockingShadowTraffic flag to make shadow traffic a blocking (instead of background) call
   * @param acceptGzip whether the client accepts gzip
   */
  private PromotedDeliveryClient(String deliveryEndpoint, String deliveryApiKey,
      long deliveryTimeoutMillis, String metricsEndpoint, String metricsApiKey,
      long metricsTimeoutMillis, boolean warmup, Executor executor,
      int maxRequestInsertions, ApplyTreatmentChecker applyTreatmentChecker,
      ApiFactory apiFactory, float shadowTrafficDeliveryRate, Sampler sampler,
      boolean performChecks, boolean blockingShadowTraffic, boolean acceptGzip) {

    if (deliveryTimeoutMillis <= 0) {
      deliveryTimeoutMillis = DEFAULT_DELIVERY_TIMEOUT_MILLIS;
    }
    if (metricsTimeoutMillis <= 0) {
      metricsTimeoutMillis = DEFAULT_METRICS_TIMEOUT_MILLIS;
    }

    if (executor == null) {
      executor = Executors.newSingleThreadExecutor();
    }
    
    if (apiFactory == null) {
      apiFactory = new DefaultApiFactory();
    }
    
    if (maxRequestInsertions <= 0 ) {
      maxRequestInsertions = DEFAULT_MAX_REQUEST_INSERTIONS;
    }
    
    if (shadowTrafficDeliveryRate < 0 || shadowTrafficDeliveryRate > 1) {
      throw new IllegalArgumentException("shadowTrafficDeliveryRate must be between 0 and 1");
    }
    
    if (sampler == null) {
      sampler = new SamplerImpl();
    }
    this.sampler = sampler;
    
    this.executor = executor;
    this.shadowTrafficDeliveryRate = shadowTrafficDeliveryRate;
    this.applyTreatmentChecker = applyTreatmentChecker;
    this.sdkDelivery = apiFactory.createSdkDelivery();
    this.apiMetrics = apiFactory.createApiMetrics(metricsEndpoint, metricsApiKey, metricsTimeoutMillis);
    this.apiDelivery = apiFactory.createApiDelivery(deliveryEndpoint, deliveryApiKey, deliveryTimeoutMillis, warmup, maxRequestInsertions, acceptGzip);
    this.performChecks = performChecks;
    this.blockingShadowTraffic = blockingShadowTraffic;
  }

  /**
   * Used to call Delivery API. Ranks the given list of Content.
   *
   * @param deliveryRequest the delivery request
   * @throws DeliveryException when any exception occurs
   */
  public DeliveryResponse deliver(DeliveryRequest deliveryRequest) throws DeliveryException {
    DeliveryPlan plan = plan(deliveryRequest.isOnlyLog(), deliveryRequest.getExperiment());
    prepareRequest(deliveryRequest, plan);

    Response apiResponse = null;
    if (plan.useApiResponse()) {
      try {
        apiResponse = apiDelivery.runDelivery(deliveryRequest);
      } catch (DeliveryException ex) {
        LOGGER.log(Level.WARNING, "Error calling Delivery API, falling back", ex);
      }
    }
    return handleSdkAndLog(deliveryRequest, plan, apiResponse);
  }

  /**
   * Returns a {@code DeliveryPlan} that determines SDK execution for
   * {@link #deliver(DeliveryRequest) }.
   *
   * <p>This method provides support for reusing/overriding parts of
   * {@link #deliver(DeliveryRequest) }.  Most users should use the {@code deliver} method instead.
   * </p>
   *
   * @param onlyLog if true, the SDK Response will use the SDK-side paged response
   * @see #deliver
   */
  public DeliveryPlan plan(boolean onlyLog, @Nullable CohortMembership experiment) {
    boolean useApiResponse = !onlyLog && shouldApplyTreatment(experiment);
    // TODO - why does checkCohortMembership copy?
    return new DeliveryPlan(generateClientRequestId(), useApiResponse);
  }

  /**
   * Prepares the {@code deliveryRequest} using the {@code plan}.
   *
   * <p>This method provides support for reusing/overriding parts of
   * {@link #deliver(DeliveryRequest) }.  Most users should use the {@code deliver} method instead.
   * </p>
   *
   * @see #deliver
   */
  public void prepareRequest(DeliveryRequest deliveryRequest, DeliveryPlan plan) {
    if (performChecks) {
      List<String> validationErrors = deliveryRequest.validate();
      for (String validationError : validationErrors) {
        LOGGER.log(Level.WARNING, "Delivery Request Validation Error", validationError);
      }
    }
    ensureClientRequestId(deliveryRequest.getRequest(), plan.getClientRequestId());
    fillInRequestFields(deliveryRequest.getRequest());
  }

  /**
   * Optionally handles SDK Delivery, logs and does shadow traffic.
   *
   * <p>This method provides support for reusing/overriding parts of
   * {@link #deliver(DeliveryRequest) }.  Most users should use the {@code deliver} method instead.
   * </p>
   *
   * @see #deliver
   */
  public DeliveryResponse handleSdkAndLog(DeliveryRequest deliveryRequest, DeliveryPlan plan,
      @Nullable Response apiResponse) throws DeliveryException {
    CohortMembership cohortMembership = cloneCohortMembership(deliveryRequest.getExperiment());

    Response response;
    ExecutionServer execSrv;
    if (apiResponse != null) {
      response = apiResponse;
      execSrv = ExecutionServer.API;
    } else {
      response = sdkDelivery.runDelivery(deliveryRequest);
      execSrv = ExecutionServer.SDK;
    }

    // Log SDK DeliveryLog to Metrics API.
    if (execSrv != ExecutionServer.API || cohortMembership != null) {
      logToMetrics(deliveryRequest, response, cohortMembership, execSrv);
    }

    // Do not send shadow traffic even if an API call was already attempted.
    if (!plan.useApiResponse() && shouldSendShadowTraffic()) {
      deliverShadowTraffic(deliveryRequest);
    }

    return new DeliveryResponse(response, deliveryRequest.getRequest().getClientRequestId(), execSrv);
  }

  /**
   * Sends shadow traffic asynchronously.
   * @param deliveryRequest
   */
  private void deliverShadowTraffic(DeliveryRequest deliveryRequest) {
    if (blockingShadowTraffic) {
      doDeliverShadowTraffic(deliveryRequest);
    } else {
      executor.execute(() -> doDeliverShadowTraffic(deliveryRequest));
    }
  }

  private void doDeliverShadowTraffic(DeliveryRequest deliveryRequest) {
    try {
      // We need a clone here in order to safely modify the ClientInfo.
      DeliveryRequest requestToSend = deliveryRequest.clone();
      
      // We ensured earlier that client info was filled in.
      assert requestToSend.getRequest().getClientInfo() != null;

      requestToSend.getRequest().getClientInfo().setClientType(ClientType.PLATFORM_SERVER);
      requestToSend.getRequest().getClientInfo().setTrafficType(TrafficType.SHADOW);
      
      apiDelivery.runDelivery(requestToSend);
    } catch (DeliveryException | CloneNotSupportedException ex) {
      LOGGER.log(Level.WARNING, "Error calling Delivery API for shadow traffic", ex);
    }
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
      return applyTreatmentChecker.shouldApplyTreatment(cohortMembership);
    }
    if (cohortMembership == null) {
      return true;
    }

    return cohortMembership.getArm() == null || cohortMembership.getArm() != CohortArm.CONTROL;
  }

  /**
   * Checks whether or not to send shadow traffic when delivery did not occur.
   * @return true to send shadow traffic, false otherwise.
   */
  private boolean shouldSendShadowTraffic() {
    return (shadowTrafficDeliveryRate > 0 && sampler.sampleRandom(shadowTrafficDeliveryRate));
  }
  
  /**
   * Clone CohortMembership from the request, based on the provided experiment.
   * If there isn't one, returns null.
   *
   * @param deliveryRequest the delivery request
   * @return the cohort membership to use, or null if none
   */
  private CohortMembership cloneCohortMembership(CohortMembership experiment) {
    if (experiment == null) {
      return null;
    }

    // Q - why does this copy the record?  I'm guessing this is to strip out other fields.
    CohortMembership cohortMembership = new CohortMembership()
        .arm(experiment.getArm())
        .cohortId(experiment.getCohortId());    
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
    executor.execute(() -> {
      LogRequest logRequest =
          createLogRequest(deliveryRequest, deliveryResponse, cohortMembership, execSrv);
      try {
        apiMetrics.runMetricsLogging(logRequest);
      } catch (DeliveryException ex) {
        LOGGER.log(Level.WARNING, "Error calling Metrics API", ex);
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

    Request request = deliveryRequest.getRequest();
    
    LogRequest logRequest = new LogRequest()
        .userInfo(request.getUserInfo())
        .clientInfo(request.getClientInfo())
        .platformId(request.getPlatformId())
        .timing(request.getTiming());

    // If delivery was done API-side, we don't need to follow up with a delivery log.
    if (execSvr != ExecutionServer.API) {    
      DeliveryLog deliveryLog = new DeliveryLog()
          .execution(new DeliveryExecution().executionServer(execSvr).serverVersion(SERVER_VERSION))
          .request(request)
          .response(response);
      logRequest.addDeliveryLogItem(deliveryLog);
    }

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
    request.getClientInfo().setClientType(ClientType.PLATFORM_SERVER);
    request.getClientInfo().setTrafficType(TrafficType.PRODUCTION);

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
   * @param clientRequestId
   */
  private void ensureClientRequestId(Request request, String clientRequestId) {
    if (request.getClientRequestId() == null || request.getClientRequestId().isBlank()) {
      request.setClientRequestId(clientRequestId);
    }
  }

  private String generateClientRequestId() {
    return UUID.randomUUID().toString();
  }

  public boolean isPerformChecks() {
    return performChecks;
  }

  public void setPerformChecks(boolean performChecks) {
    this.performChecks = performChecks;
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

    /** The executor. */
    private Executor executor;

    /** The apply treatment checker. */
    private ApplyTreatmentChecker applyTreatmentChecker;

    /** The API factory. */
    private ApiFactory apiFactory;
    
    /** Maximum number of request insertions. */
    private int maxRequestInsertions;
    
    /** The shadow traffic delivery rate in the range [0, 1] **/
    private float shadowTrafficDeliveryRate;
    
    /** The sampler to use */
    private Sampler sampler;
    
    /** The perform-checks value. */
    private boolean performChecks;
    
    /** The blocking shadow traffic value */
    private boolean blockingShadowTraffic;

    /** The acceptsGzip value. */
    private boolean acceptGzip = true;

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
      this.metricsEndpoint = metricsEndpoint;
      return this;
    }

    /**
     * Sets metrics aoi key.
     *
     * @param metricsApiKey the metrics api key
     * @return the builder
     */
    public Builder withMetricsApiKey(String metricsApiKey) {
      this.metricsApiKey = metricsApiKey;
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
     * Sets performChecks, which will optionally cause extra validations to occur on delivery requests.
     *
     * @param performChecks the performChecks value
     * @return the builder
     */
    public Builder witPerformChecks(boolean performChecks) {
      this.performChecks = performChecks;
      return this;
    }

    /**
     * Sets blockingShadowTraffic, which determines whether or not shadow traffic delivery is a blocking call.
     *
     * @param blockingShadowTraffic the blockingShadowTraffic value
     * @return the builder
     */
    public Builder withBlockingShadowTraffic(boolean blockingShadowTraffic) {
      this.blockingShadowTraffic = blockingShadowTraffic;
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
     * Sets executor.
     *
     * @param executor the metrics executor
     * @return the builder
     */
    public Builder withExecutor(Executor executor) {
      this.executor = executor;
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
     * Sets API factory.
     *
     * @param apiFactory the API factory
     * @return the builder
     */
    public Builder withApiFactory(ApiFactory apiFactory) {
      this.apiFactory = apiFactory;
      return this;
    }

    /**
     * Sets max request insertions.
     *
     * @param maxRequestInsertions the max request insertions
     * @return the builder
     */
    public Builder withMaxRequestInsertions(int maxRequestInsertions) {
      this.maxRequestInsertions = maxRequestInsertions;
      return this;
    }

    /**
     * Sets shadow traffic delivery rate.
     *
     * @param shadowTrafficDeliveryRate the shadow traffic delivery rate
     * @return the builder
     */
    public Builder withShadowTrafficDeliveryRate(float shadowTrafficDeliveryRate) {
      this.shadowTrafficDeliveryRate = shadowTrafficDeliveryRate;
      return this;
    }

    /**
     * Sets sampler, which we use for sampling shadow traffic.
     *
     * @param sampler the sampler
     * @return the builder
     */
    public Builder withSampler(Sampler sampler) {
      this.sampler = sampler;
      return this;
    }

    /**
     * Sets accept gzip encoding header.  Defauls to true.
     *
     * @param acceptGzip accepts gzip
     * @return the builder
     */
    public Builder withAcceptGzip(boolean acceptGzip) {
      this.acceptGzip = acceptGzip;
      return this;
    }

    /**
     * Builds the {@link PromotedDeliveryClient}.
     *
     * @return the promoted delivery client
     */
    public PromotedDeliveryClient build() {
      return new PromotedDeliveryClient(deliveryEndpoint, deliveryApiKey, deliveryTimeoutMillis,
          metricsEndpoint, metricsApiKey, metricsTimeoutMillis, warmup, executor,
          maxRequestInsertions, applyTreatmentChecker, apiFactory, shadowTrafficDeliveryRate,
          sampler, performChecks, blockingShadowTraffic, acceptGzip);
    }
  }
}
