package ai.promoted.delivery.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.promoted.delivery.model.LogRequest;

/**
 * Client for Promoted.ai's Metrics API.
 */
public class ApiMetrics implements Metrics {
  private static final Logger LOGGER = Logger.getLogger(ApiMetrics.class.getName());

  /** The Metrics API endpoint (get this from Promoted.ai). */
  private final String endpoint;

  /** The api key (get this from Promoted.ai). */
  private final String apiKey;

  /** The http client. */
  private final HttpClient httpClient;

  /** JSON mapper. */
  private final ObjectMapper mapper;

  /** The timeout duration. */
  private final Duration timeoutDuration;

  /**
   * Instantiates a new Metrics API client.
   *
   * @param endpoint the endpoint
   * @param apiKey the api key
   * @param timeoutMillis the timeout in millis
   */
  public ApiMetrics(String endpoint, String apiKey, long timeoutMillis) {
    this.endpoint = endpoint;
    this.apiKey = apiKey;
    this.mapper = new ObjectMapper();
    this.mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
    this.mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    this.timeoutDuration = Duration.of(timeoutMillis, ChronoUnit.MILLIS);

    this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  }

  /**
   * Do metrics logging.
   *
   * @param logRequest the log request
   * @throws DeliveryException any delivery exception that may occur
   */
  @Override
  public void runMetricsLogging(LogRequest logRequest) throws DeliveryException {
    try {
      String requestBody = mapper.writeValueAsString(logRequest);
      // TODO: Compression (does metrics accept that?).
      HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(endpoint))
          .header("Content-Type", "application/json")
          .header("x-api-key", apiKey).timeout(timeoutDuration)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

      HttpResponse<String> response =
          httpClient.send(httpReq, HttpResponse.BodyHandlers.ofString());
      if (response.statusCode() < 200 || 300 <= response.statusCode()) {
        LOGGER.warning(() -> "Failure calling Metrics API; statusCode="  + response.statusCode()
          + ", body=" + response.body());
      }
    } catch (Exception ex) {
      throw new DeliveryException("Error logging to metrics", ex);
    }
  }
}
