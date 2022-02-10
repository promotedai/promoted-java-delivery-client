package ai.promoted.java.client;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.promoted.delivery.model.Response;

/**
 * Client for Promoted.ai's Delivery API.
 */
public class ApiDelivery {

  private static final Logger LOGGER = Logger.getLogger(ApiDelivery.class.getName());

  /** The Delivery API endpoint (get this from Promoted.ai). */
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
   * Instantiates a new Delivery API client.
   *
   * @param endpoint the endpoint
   * @param apiKey the api key
   * @param timeoutMillis the timeout in millis
   */
  public ApiDelivery(String endpoint, String apiKey, long timeoutMillis) {
    this.endpoint = endpoint;
    this.apiKey = apiKey;
    this.mapper = new ObjectMapper();
    this.timeoutDuration = Duration.of(timeoutMillis, ChronoUnit.MILLIS);

    this.httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();
  }

  /**
   * Do delivery.
   *
   * @param deliveryRequest the delivery request
   * @return the response with insertions from Promoted.ai
   * @throws DeliveryException any delivery exception that may occur
   */
  public Response runDelivery(DeliveryRequest deliveryRequest) throws DeliveryException {
    try {
      String requestBody = mapper.writeValueAsString(deliveryRequest.getRequest());
      // TODO: Compression, simple example here
      // https://golb.hplar.ch/2019/01/java-11-http-client.html
      HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(endpoint))
          .header("x-api-key", apiKey).timeout(timeoutDuration)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

      HttpResponse<String> response =
          httpClient.send(httpReq, HttpResponse.BodyHandlers.ofString());
      return mapper.readValue(response.body(), Response.class);
    } catch (Exception ex) {
      throw new DeliveryException("Error running delivery", ex);
    }
  }

  /**
   * Do warmup.
   */
  public void runWarmup() {
    String warmupEndpoint = endpoint.replace("/deliver", "/health");
    for (int i = 0; i < 20; i++) {
      try {
        HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(warmupEndpoint))
            .header("x-api-key", apiKey).GET().build();
        httpClient.send(httpReq, HttpResponse.BodyHandlers.ofString());
      } catch (Exception ex) {
        LOGGER.warning("Error during warmup");
      }
    }
  }
}
