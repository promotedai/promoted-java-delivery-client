/*
 * 
 */
package ai.promoted.delivery.model;

public enum CohortArm {
  UNKNOWN(0), CONTROL(1), TREATMENT(2), TREATMENT1(3), TREATMENT2(4), TREATMENT3(5);

  private final int value;

  CohortArm(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
