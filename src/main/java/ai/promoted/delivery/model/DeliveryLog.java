package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({DeliveryLog.JSON_PROPERTY_REQUEST, DeliveryLog.JSON_PROPERTY_RESPONSE,
    DeliveryLog.JSON_PROPERTY_EXECUTION})
public class DeliveryLog {
  public static final String JSON_PROPERTY_REQUEST = "request";
  private Request request;

  public static final String JSON_PROPERTY_RESPONSE = "response";
  private Response response;

  public static final String JSON_PROPERTY_EXECUTION = "execution";
  private DeliveryExecution execution;

  public DeliveryLog() {}

  public DeliveryLog request(Request request) {
    this.request = request;
    return this;
  }

  /**
   * Get request
   * 
   * @return request
   **/
  @JsonProperty(JSON_PROPERTY_REQUEST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Request getRequest() {
    return request;
  }


  @JsonProperty(JSON_PROPERTY_REQUEST)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRequest(Request request) {
    this.request = request;
  }

  public DeliveryLog response(Response response) {
    this.response = response;
    return this;
  }

  /**
   * Get response
   * 
   * @return response
   **/
  @JsonProperty(JSON_PROPERTY_RESPONSE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Response getResponse() {
    return response;
  }


  @JsonProperty(JSON_PROPERTY_RESPONSE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setResponse(Response response) {
    this.response = response;
  }

  public DeliveryLog execution(DeliveryExecution execution) {
    this.execution = execution;
    return this;
  }

  /**
   * Get execution
   * 
   * @return execution
   **/
  @JsonProperty(JSON_PROPERTY_EXECUTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public DeliveryExecution getExecution() {
    return execution;
  }


  @JsonProperty(JSON_PROPERTY_EXECUTION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setExecution(DeliveryExecution execution) {
    this.execution = execution;
  }


  /**
   * Return true if this DeliveryLog object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeliveryLog deliveryLog = (DeliveryLog) o;
    return Objects.equals(this.request, deliveryLog.request)
        && Objects.equals(this.response, deliveryLog.response)
        && Objects.equals(this.execution, deliveryLog.execution);
  }

  @Override
  public int hashCode() {
    return Objects.hash(request, response, execution);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeliveryLog {\n");
    sb.append("    request: ").append(toIndentedString(request)).append("\n");
    sb.append("    response: ").append(toIndentedString(response)).append("\n");
    sb.append("    execution: ").append(toIndentedString(execution)).append("\n");
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
