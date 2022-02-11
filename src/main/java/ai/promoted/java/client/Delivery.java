package ai.promoted.java.client;

import ai.promoted.delivery.model.Response;


public interface Delivery {

  /**
   * Do delivery.
   *
   * @param deliveryRequest the delivery request
   * @return the populated response
   */
  Response runDelivery(DeliveryRequest deliveryRequest) throws DeliveryException;

}
