package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ai.promoted.proto.delivery.ExecutionServer;

/**
 * DeliveryExecution
 */
@JsonPropertyOrder({DeliveryExecution.JSON_PROPERTY_EXECUTION_SERVER,
    DeliveryExecution.JSON_PROPERTY_SERVER_VERSION})
public class DeliveryExecution {
  public static final String JSON_PROPERTY_EXECUTION_SERVER = "executionServer";
  private ExecutionServer executionServer;

  public static final String JSON_PROPERTY_SERVER_VERSION = "serverVersion";
  private String serverVersion;

  public DeliveryExecution() {}

  public DeliveryExecution executionServer(ExecutionServer executionServer) {
    this.executionServer = executionServer;
    return this;
  }

  /**
   * Get executionServer
   * 
   * @return executionServer
   **/
  @JsonProperty(JSON_PROPERTY_EXECUTION_SERVER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ExecutionServer getExecutionServer() {
    return executionServer;
  }


  @JsonProperty(JSON_PROPERTY_EXECUTION_SERVER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setExecutionServer(ExecutionServer executionServer) {
    this.executionServer = executionServer;
  }


  public DeliveryExecution serverVersion(String serverVersion) {
    this.serverVersion = serverVersion;
    return this;
  }

  /**
   * Get serverVersion
   * 
   * @return serverVersion
   **/
  @JsonProperty(JSON_PROPERTY_SERVER_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getServerVersion() {
    return serverVersion;
  }


  @JsonProperty(JSON_PROPERTY_SERVER_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setServerVersion(String serverVersion) {
    this.serverVersion = serverVersion;
  }


  /**
   * Return true if this DeliveryExecution object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    DeliveryExecution deliveryExecution = (DeliveryExecution) o;
    return Objects.equals(this.serverVersion, deliveryExecution.serverVersion)
        && Objects.equals(this.executionServer, deliveryExecution.executionServer);
  }

  @Override
  public int hashCode() {
    return Objects.hash(serverVersion, executionServer);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class DeliveryExecution {\n");
    sb.append("    serverVersion: ").append(toIndentedString(serverVersion)).append("\n");
    sb.append("    executionServer: ").append(toIndentedString(executionServer)).append("\n");
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

