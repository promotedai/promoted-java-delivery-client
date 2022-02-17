package ai.promoted.delivery.client;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;
import ai.promoted.delivery.model.Response;

/**
 * Client for Promoted.ai's Delivery API.
 */
public class ApiDelivery implements Delivery  {

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
   * @param warmup 
   */
  public ApiDelivery(String endpoint, String apiKey, long timeoutMillis, boolean warmup) {
    this.endpoint = endpoint;
    this.apiKey = apiKey;
    this.mapper = new ObjectMapper();
    this.timeoutDuration = Duration.of(timeoutMillis, ChronoUnit.MILLIS);

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
    try {
      String requestBody = mapper.writeValueAsString(deliveryRequest.getRequest());
      
      HttpRequest httpReq = HttpRequest.newBuilder().uri(URI.create(endpoint))
          .header("Accept-Encoding", "gzip")
          .header("x-api-key", apiKey)
          .timeout(timeoutDuration)
          .POST(HttpRequest.BodyPublishers.ofString(requestBody)).build();

      HttpResponse<InputStream> response =
          httpClient.send(httpReq, HttpResponse.BodyHandlers.ofInputStream());
      String encoding = response.headers().firstValue("Content-Encoding").orElse("");
      
      if (encoding.equals("gzip")) {
        return processCompressedResponse(response);
      }
      else {
        return processUncompressedResponse(response);
      }      
    } catch (Exception ex) {
      throw new DeliveryException("Error running delivery", ex);
    }
  }

  private Response processUncompressedResponse(HttpResponse<InputStream> response)
      throws IOException, StreamReadException, DatabindException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (var is = response.body(); var autoCloseOs = os) {
      is.transferTo(autoCloseOs);
    }
    return mapper.readValue(os.toByteArray(), Response.class);
  }

  private Response processCompressedResponse(HttpResponse<InputStream> response)
      throws IOException, StreamReadException, DatabindException {
    ByteArrayOutputStream os = new ByteArrayOutputStream();
    try (InputStream is = new GZIPInputStream(response.body()); var autoCloseOs = os) {
      is.transferTo(autoCloseOs);
    }
    return mapper.readValue(os.toByteArray(), Response.class);
  }

  /**
   * Do warmup.
   */
  private void runWarmup() {
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
