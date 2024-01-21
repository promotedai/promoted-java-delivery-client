package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.Response;

class ApiDeliveryTest {

  @Test
  void validate() throws DeliveryException {
    Response response = new Response();
    response.setRequestId("reqid");
    ApiDelivery.validate(response);
  }

  @Test
  void validate_missingRequestId() {
    Response response = new Response();
    assertThrows(DeliveryException.class, () -> ApiDelivery.validate(response));
  }

  @Test
  void replaceSuffix() {
    assertEquals("test", ApiDelivery.replaceSuffix("test", "not", "there"));
    assertEquals("test", ApiDelivery.replaceSuffix("test", "", ""));
    assertEquals("tess", ApiDelivery.replaceSuffix("test", "t", "s"));
    assertEquals("take", ApiDelivery.replaceSuffix("test", "est", "ake"));
    assertEquals("test", ApiDelivery.replaceSuffix("test", "abtest", "actess"));
    assertEquals("testtake", ApiDelivery.replaceSuffix("testtest", "est", "ake"));
    assertEquals("http://delivery.example.com/healthz", ApiDelivery.replaceSuffix("http://delivery.example.com/deliver", "/deliver", "/healthz"));
  }
}
