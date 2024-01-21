package ai.promoted.delivery.client;

import ai.promoted.proto.delivery.Response;


public interface Delivery {

  /**
   * Do delivery.
   *
   * @param deliveryRequest the delivery request
   * @return the populated response
   */
  Response runDelivery(DeliveryRequest deliveryRequest) throws DeliveryException;

}
