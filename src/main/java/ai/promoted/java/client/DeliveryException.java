package ai.promoted.java.client;

public class DeliveryException extends Exception {
  private static final long serialVersionUID = -5346305939046085966L;

  public DeliveryException() {}

  public DeliveryException(String message) {
    super(message);
  }

  public DeliveryException(String message, Throwable cause) {
    super(message, cause);
  }
}
