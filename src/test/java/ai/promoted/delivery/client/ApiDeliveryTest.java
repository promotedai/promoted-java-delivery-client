package ai.promoted.delivery.client;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import ai.promoted.delivery.model.Request;
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
}
