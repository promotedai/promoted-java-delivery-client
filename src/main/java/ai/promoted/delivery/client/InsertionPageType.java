package ai.promoted.delivery.client;

public enum InsertionPageType {
  UNPAGED(1), PREPAGED(2);

  private final int value;

  InsertionPageType(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
