package ai.promoted.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Response
 */
@JsonPropertyOrder({
  Response.JSON_PROPERTY_REQUEST_ID,
  Response.JSON_PROPERTY_INSERTION,
  Response.JSON_PROPERTY_PAGING_INFO,
  Response.JSON_PROPERTY_INTROSPECTION_DATA})
public class Response {
  public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
  private String requestId;

  public static final String JSON_PROPERTY_INSERTION = "insertion";
  private List<Insertion> insertion = new ArrayList<>();

  public static final String JSON_PROPERTY_PAGING_INFO = "pagingInfo";
  private PagingInfo pagingInfo;

  public static final String JSON_PROPERTY_INTROSPECTION_DATA= "introspectionData";
  private String introspectionData;

  public Response() {}


  /**
   * Get request ID
   * 
   * @return requestId
   **/
  @JsonProperty(JSON_PROPERTY_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getRequestId() {
    return requestId;
  }

  @JsonProperty(JSON_PROPERTY_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRequestId(String requestId) {
    this.requestId = requestId;
  }

  public Response insertion(List<Insertion> insertion) {
    if (insertion == null) {
      throw new IllegalArgumentException("insertion needs to be non-null");
    }
    this.insertion = insertion;
    return this;
  }

  public Response addInsertionItem(Insertion insertionItem) {
    this.insertion.add(insertionItem);
    return this;
  }

  /**
   * Get insertion
   * 
   * @return insertion
   **/
  @JsonProperty(JSON_PROPERTY_INSERTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<Insertion> getInsertion() {
    return insertion;
  }


  @JsonProperty(JSON_PROPERTY_INSERTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInsertion(List<Insertion> insertion) {
    this.insertion = insertion;
  }


  public Response pagingInfo(PagingInfo pagingInfo) {
    this.pagingInfo = pagingInfo;
    return this;
  }


  public Response introspectionData(String introspectionData) {
    this.introspectionData = introspectionData;
    return this;
  }

  /**
   * Get introspection data
   * 
   * @return introspectionData
   **/
  @JsonProperty(JSON_PROPERTY_INTROSPECTION_DATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getIntrospectionData() {
    return introspectionData;
  }


  @JsonProperty(JSON_PROPERTY_INTROSPECTION_DATA)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIntrospectionData(String introspectionData) {
    this.introspectionData = introspectionData;
  }

  /**
   * Get pagingInfo
   * 
   * @return pagingInfo
   **/
  @JsonProperty(JSON_PROPERTY_PAGING_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public PagingInfo getPagingInfo() {
    return pagingInfo;
  }


  @JsonProperty(JSON_PROPERTY_PAGING_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPagingInfo(PagingInfo pagingInfo) {
    this.pagingInfo = pagingInfo;
  }


  /**
   * Return true if this Response object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Response response = (Response) o;
    return Objects.equals(this.requestId, response.requestId)
        && Objects.equals(this.insertion, response.insertion)
        && Objects.equals(this.pagingInfo, response.pagingInfo)
        && Objects.equals(this.introspectionData, response.introspectionData);
  }

  @Override
  public int hashCode() {
    return Objects.hash(insertion, pagingInfo, introspectionData);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Response {\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    insertion: ").append(toIndentedString(insertion)).append("\n");
    sb.append("    introspectionData: ").append(toIndentedString(introspectionData)).append("\n");
    sb.append("    pagingInfo: ").append(toIndentedString(pagingInfo)).append("\n");
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

