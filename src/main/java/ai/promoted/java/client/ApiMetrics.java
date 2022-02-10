package ai.promoted.java.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.promoted.delivery.model.LogRequest;
import ai.promoted.delivery.model.Response;

/**
 * Client for Promoted.ai's Metrics API.
 */
public class ApiMetrics {

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
    this.timeoutDuration = Duration.of(timeoutMillis, ChronoUnit.MILLIS);

    this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  }

  /**
   * Do metrics logging.
   *
   * @param logRequest the log request
   * @return the response
   * @throws DeliveryException any delivery exception that may occur
   */
  public Response runMetricsLogging(LogRequest logRequest) throws DeliveryException {
    try {
      String requestBody = mapper.writeValueAsString(logRequest);
      // TODO: Compression (does metrics accept that?).
      HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(endpoint))
          .header("x-api-key", apiKey).timeout(timeoutDuration)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

      HttpResponse<String> response =
          httpClient.send(httpReq, HttpResponse.BodyHandlers.ofString());
      return mapper.readValue(response.body(), Response.class);
    } catch (Exception ex) {
      throw new DeliveryException("Error logging to metrics", ex);
    }
  }
}
