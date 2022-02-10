package ai.promoted.delivery.model;

public enum ExecutionServer {
  UNKNOWN(0), API(1), SDK(2);

  private final int value;

  ExecutionServer(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
