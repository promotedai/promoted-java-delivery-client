package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Paging
 */
@JsonPropertyOrder({Paging.JSON_PROPERTY_PAGING_ID, Paging.JSON_PROPERTY_CURSOR,
    Paging.JSON_PROPERTY_SIZE, Paging.JSON_PROPERTY_OFFSET})
public class Paging {
  public static final String JSON_PROPERTY_PAGING_ID = "pagingId";
  private String pagingId;

  public static final String JSON_PROPERTY_CURSOR = "cursor";
  private String cursor;

  public static final String JSON_PROPERTY_SIZE = "size";
  private Integer size;

  public static final String JSON_PROPERTY_OFFSET = "offset";
  private Integer offset;

  public Paging() {}


  public Paging pagingId(String pagingId) {
    this.pagingId = pagingId;
    return this;
  }

  /**
   * Get pagingId
   * 
   * @return pagingId
   **/
  @JsonProperty(JSON_PROPERTY_PAGING_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPagingId() {
    return pagingId;
  }


  @JsonProperty(JSON_PROPERTY_PAGING_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPagingId(String pagingId) {
    this.pagingId = pagingId;
  }

  public Paging cursor(String cursor) {
    this.cursor = cursor;
    return this;
  }

  /**
   * Get cursor
   * 
   * @return cursor
   **/
  @JsonProperty(JSON_PROPERTY_CURSOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCursor() {
    return cursor;
  }


  @JsonProperty(JSON_PROPERTY_CURSOR)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCursor(String cursor) {
    this.cursor = cursor;
  }


  public Paging size(Integer size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * 
   * @return size
   **/
  @JsonProperty(JSON_PROPERTY_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getSize() {
    return size;
  }


  @JsonProperty(JSON_PROPERTY_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSize(Integer size) {
    this.size = size;
  }

  public Paging offset(Integer offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Get offset
   * 
   * @return offset
   **/
  @JsonProperty(JSON_PROPERTY_OFFSET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getOffset() {
    return offset;
  }


  @JsonProperty(JSON_PROPERTY_OFFSET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOffset(Integer offset) {
    this.offset = offset;
  }

  /**
   * Return true if this Paging object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Paging paging = (Paging) o;
    return Objects.equals(this.offset, paging.offset)
        && Objects.equals(this.pagingId, paging.pagingId)
        && Objects.equals(this.cursor, paging.cursor) && Objects.equals(this.size, paging.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(offset, pagingId, cursor, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Paging {\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    pagingId: ").append(toIndentedString(pagingId)).append("\n");
    sb.append("    cursor: ").append(toIndentedString(cursor)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces (except the first line).
   */
  private String toIndentedString(Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

