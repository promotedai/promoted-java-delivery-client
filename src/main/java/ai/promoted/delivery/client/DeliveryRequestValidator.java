package ai.promoted.delivery.client;

import java.util.List;

/**
 * Performs validation on delivery requests during a deliver call when performChecks is true in the client.
 */
public interface DeliveryRequestValidator {
  /**
   * Checks the state of this delivery requests and collects/returns any validation errors.
   * @param request the delivery request to validate 
   * @param isShadowTraffic whether or not we're in shadow traffic mode, which changes some validation logic
   * 
   * @return a list of validation errors, which may be empty.
   */
  List<String> validate(DeliveryRequest request, boolean isShadowTraffic);
}
