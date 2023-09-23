/*
 * 
 */
package ai.promoted.delivery.model;

public enum ClientType {
  UNKNOWN(0), PLATFORM_SERVER(1), PLATFORM_CLIENT(2);

  private final int value;

  ClientType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
