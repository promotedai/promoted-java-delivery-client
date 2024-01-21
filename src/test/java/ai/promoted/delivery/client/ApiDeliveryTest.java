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
  void removeDeliverSuffix() {
    assertEquals("http://delivery.example.com", ApiDelivery.removeDeliverSuffix("http://delivery.example.com/deliver"));
    assertEquals("http://delivery.example.com", ApiDelivery.removeDeliverSuffix("http://delivery.example.com"));
  }
}
