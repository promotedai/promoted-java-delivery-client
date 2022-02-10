/*
 * 
 */
package ai.promoted.delivery.model;

public enum ClientType {
  UNKNOWN(0), SERVER(1), CLIENT(2);

  private final int value;

  ClientType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
