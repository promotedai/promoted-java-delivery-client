package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ClientInfo
 */
@JsonPropertyOrder({ClientInfo.JSON_PROPERTY_CLIENT_TYPE, ClientInfo.JSON_PROPERTY_TRAFFIC_TYPE})
public class ClientInfo implements Cloneable {
  public static final String JSON_PROPERTY_CLIENT_TYPE = "clientType";
  private ClientType clientType;

  public static final String JSON_PROPERTY_TRAFFIC_TYPE = "trafficType";
  private TrafficType trafficType;

  public ClientInfo() {}

  public ClientInfo clientType(ClientType clientType) {
    this.clientType = clientType;
    return this;
  }

  /**
   * Get clientType
   * 
   * @return clientType
   **/
  @JsonProperty(JSON_PROPERTY_CLIENT_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ClientType getClientType() {
    return clientType;
  }


  @JsonProperty(JSON_PROPERTY_CLIENT_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setClientType(ClientType clientType) {
    this.clientType = clientType;
  }


  public ClientInfo trafficType(TrafficType trafficType) {
    this.trafficType = trafficType;
    return this;
  }

  /**
   * Get trafficType
   * 
   * @return trafficType
   **/
  @JsonProperty(JSON_PROPERTY_TRAFFIC_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TrafficType getTrafficType() {
    return trafficType;
  }


  @JsonProperty(JSON_PROPERTY_TRAFFIC_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTrafficType(TrafficType trafficType) {
    this.trafficType = trafficType;
  }


  /**
   * Return true if this ClientInfo object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientInfo clientInfo = (ClientInfo) o;
    return Objects.equals(this.clientType, clientInfo.clientType)
        && Objects.equals(this.trafficType, clientInfo.trafficType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientType, trafficType);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientInfo {\n");
    sb.append("    clientType: ").append(toIndentedString(clientType)).append("\n");
    sb.append("    trafficType: ").append(toIndentedString(trafficType)).append("\n");
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

  /**
   * Gets a clone of the ClientInfo.
   */
  public ClientInfo clone() throws CloneNotSupportedException {
    return (ClientInfo) super.clone();
  }
}

