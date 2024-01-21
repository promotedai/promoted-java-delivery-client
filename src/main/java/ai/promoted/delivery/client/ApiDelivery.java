package ai.promoted.delivery.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import com.google.protobuf.util.JsonFormat;
import ai.promoted.proto.delivery.Response;

/**
 * Client for Promoted.ai's Delivery API.
 */
public class ApiDelivery implements Delivery  {

  private static final Logger LOGGER = Logger.getLogger(ApiDelivery.class.getName());

  private static final String DELIVER_SUFFIX = "/deliver";
  private static final String HEALTHZ_SUFFIX = "/healthz";

  /** The Delivery API endpoint (get this from Promoted.ai). */
  private final String baseEndpoint;
  private final String deliverEndpoint;
  private final String healthzEndpoint;

  /** The api key (get this from Promoted.ai). */
  private final String apiKey;

  /** The http client. */
  private final HttpClient httpClient;

  /** The timeout duration. */
  private final Duration timeoutDuration;

  /**
   * Maximum number of request insertions passed to delivery API. Any request insertions
   * beyond this limit will be returned in the provided order at the end of the response
   * list.
   */
  private final int maxRequestInsertions;

  /**
   * Whether to accept gzip.
   */
  private final boolean acceptGzip;

  /**
   * Instantiates a new Delivery API client.
   *
   * @param endpoint the endpoint
   * @param apiKey the api key
   * @param timeoutMillis the timeout in millis
   * @param maxRequestInsertions the max number of request insertions
   * @param acceptGzip whether to accept gzip
   * @param warmup 
   */
  public ApiDelivery(String endpoint, String apiKey, long timeoutMillis, boolean warmup, int maxRequestInsertions, boolean acceptGzip) {
    this.baseEndpoint = removeDeliverSuffix(endpoint);
    this.deliverEndpoint = this.baseEndpoint + DELIVER_SUFFIX;
    this.healthzEndpoint = this.baseEndpoint + HEALTHZ_SUFFIX;

    this.apiKey = apiKey;

    this.timeoutDuration = Duration.of(timeoutMillis, ChronoUnit.MILLIS);
    this.maxRequestInsertions = maxRequestInsertions;
    this.acceptGzip = acceptGzip;

    this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
    
    if (warmup) {
      runWarmup();
    }
  }

  /**
   * Do delivery.
   *
   * @param deliveryRequest the delivery request
   * @return the response with insertions from Promoted.ai
   * @throws DeliveryException any delivery exception that may occur
   */
  @Override
  public Response runDelivery(DeliveryRequest deliveryRequest) throws DeliveryException {
    DeliveryRequestState state = new DeliveryRequestState(deliveryRequest);
    Response resp;
    
    try {
      String requestBody = JsonFormat.printer().print(state.getRequestToSend(maxRequestInsertions));
      
      HttpRequest.Builder httpReqBuilder = HttpRequest.newBuilder().uri(URI.create(deliverEndpoint))
          .header("Content-Type", "application/json")
          .header("x-api-key", apiKey);
      if (acceptGzip) {
        httpReqBuilder.header("Accept-Encoding", "gzip");
      }
      httpReqBuilder
          .timeout(timeoutDuration)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody));
      HttpRequest httpReq = httpReqBuilder.build();

      HttpResponse<InputStream> response =
          httpClient.send(httpReq, HttpResponse.BodyHandlers.ofInputStream());
      String encoding = response.headers().firstValue("Content-Encoding").orElse("");
      
      if (encoding.equals("gzip")) {
        resp = processCompressedResponse(response);
      }
      else {
        resp = processUncompressedResponse(response);
      }
    } catch (Exception ex) {
      throw new DeliveryException("Error running delivery", ex);
    }
    validate(resp);
    return state.getResponseToReturn(resp);
  }

  private Response processUncompressedResponse(HttpResponse<InputStream> response) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (var is = response.body(); var autoCloseOs = os) {
      is.transferTo(autoCloseOs);
    }
    String json = new String(os.toByteArray(), StandardCharsets.UTF_8);
    Response.Builder respBuilder = Response.newBuilder();
    JsonFormat.parser().ignoringUnknownFields().merge(json, respBuilder);
    return respBuilder.build();
  }

  private Response processCompressedResponse(HttpResponse<InputStream> response) throws IOException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (InputStream is = new GZIPInputStream(response.body()); var autoCloseOs = os) {
      is.transferTo(autoCloseOs);
    }
    String json = new String(os.toByteArray(), StandardCharsets.UTF_8);
    Response.Builder respBuilder = Response.newBuilder();
    JsonFormat.parser().ignoringUnknownFields().merge(json, respBuilder);
    return respBuilder.build();
  }

  // @VisibleForTesting
  static void validate(Response response) throws DeliveryException {
    if (response.getRequestId().isBlank()) {
      throw new DeliveryException("Delivery Response should contain a requestId");
    }
  }

  /**
   * Do warmup.
   */
  private void runWarmup() {
    for (int i = 0; i < 20; i++) {
      try {
        HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(healthzEndpoint))
            .header("x-api-key", apiKey).GET().build();
        httpClient.send(httpReq, HttpResponse.BodyHandlers.ofString());
      } catch (Exception ex) {
        LOGGER.log(Level.WARNING, "Error during warmup", ex);
      }
    }
  }

  static String removeDeliverSuffix(String endpoint) {
    if (endpoint.endsWith(DELIVER_SUFFIX)) {
      return endpoint.substring(0, endpoint.length() - DELIVER_SUFFIX.length());
    }
    return endpoint;
}
}
