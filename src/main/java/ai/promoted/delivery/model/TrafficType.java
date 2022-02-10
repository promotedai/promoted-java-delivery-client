/*
 * 
 */
package ai.promoted.delivery.model;

public enum TrafficType {
  UNKNOWN(0), PRODUCTION(1);

  private final int value;

  TrafficType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
