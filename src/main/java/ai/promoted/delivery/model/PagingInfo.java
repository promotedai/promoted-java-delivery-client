package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * PagingInfo
 */
@JsonPropertyOrder({PagingInfo.JSON_PROPERTY_CURSOR, PagingInfo.JSON_PROPERTY_PAGING_ID})
public class PagingInfo {
  public static final String JSON_PROPERTY_CURSOR = "cursor";
  private String cursor;

  public static final String JSON_PROPERTY_PAGING_ID = "pagingId";
  private String pagingId;

  public PagingInfo() {}

  public PagingInfo cursor(String cursor) {
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


  public PagingInfo pagingId(String pagingId) {
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


  /**
   * Return true if this PagingInfo object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    PagingInfo pagingInfo = (PagingInfo) o;
    return Objects.equals(this.cursor, pagingInfo.cursor)
        && Objects.equals(this.pagingId, pagingInfo.pagingId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(cursor, pagingId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class PagingInfo {\n");
    sb.append("    cursor: ").append(toIndentedString(cursor)).append("\n");
    sb.append("    pagingId: ").append(toIndentedString(pagingId)).append("\n");
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

