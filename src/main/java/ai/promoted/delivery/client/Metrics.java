package ai.promoted.delivery.client;

import ai.promoted.delivery.model.LogRequest;

public interface Metrics {

  /**
   * Do metrics logging.
   *
   * @param logRequest the log request
   * @throws DeliveryException any delivery exception that may occur
   */
  void runMetricsLogging(LogRequest logRequest) throws DeliveryException;

}
