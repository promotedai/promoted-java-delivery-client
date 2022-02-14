package ai.promoted.delivery.model;

public enum UseCase {
  UNKNOWN(0),
  CUSTOM(1),
  SEARCH(2),
  SEARCH_SUGGESTIONS(3),
  FEED(4),
  RELATED_CONTENT(5),
  CLOSE_UP(6),
  CATEGORY_CONTENT(7),
  MY_CONTENT(8),
  MY_SAVED_CONTENT(9),
  SELLER_CONTENT(10),
  DISCOVER(11);

  private final int value;

  UseCase(int value) {
    this.value = value;
  }

  public int getValue() {
    return value;
  }
}
