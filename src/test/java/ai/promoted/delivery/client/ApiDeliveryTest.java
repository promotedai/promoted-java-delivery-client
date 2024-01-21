package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.proto.delivery.Response;

class ApiDeliveryTest {

  @Test
  void validate() throws DeliveryException {
    ApiDelivery.validate(Response.newBuilder().setRequestId("reqid").build());
  }

  @Test
  void validate_missingRequestId() {
    assertThrows(DeliveryException.class, () -> ApiDelivery.validate(Response.getDefaultInstance()));
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
