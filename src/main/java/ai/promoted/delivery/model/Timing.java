/*
 * 
 */
package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Timing
 */
@JsonPropertyOrder({Timing.JSON_PROPERTY_CLIENT_LOG_TIMESTAMP,
    Timing.JSON_PROPERTY_EVENT_API_TIMESTAMP, Timing.JSON_PROPERTY_LOG_TIMESTAMP})
public class Timing {
  public static final String JSON_PROPERTY_CLIENT_LOG_TIMESTAMP = "clientLogTimestamp";
  private Long clientLogTimestamp;

  public static final String JSON_PROPERTY_EVENT_API_TIMESTAMP = "eventApiTimestamp";
  private Long eventApiTimestamp;

  public static final String JSON_PROPERTY_LOG_TIMESTAMP = "logTimestamp";
  private Long logTimestamp;

  public Timing() {}

  public Timing clientLogTimestamp(Long clientLogTimestamp) {
    this.clientLogTimestamp = clientLogTimestamp;
    return this;
  }

  /**
   * Get clientLogTimestamp
   * 
   * @return clientLogTimestamp
   **/
  @JsonProperty(JSON_PROPERTY_CLIENT_LOG_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getClientLogTimestamp() {
    return clientLogTimestamp;
  }


  @JsonProperty(JSON_PROPERTY_CLIENT_LOG_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setClientLogTimestamp(Long clientLogTimestamp) {
    this.clientLogTimestamp = clientLogTimestamp;
  }


  public Timing eventApiTimestamp(Long eventApiTimestamp) {
    this.eventApiTimestamp = eventApiTimestamp;
    return this;
  }

  /**
   * Get eventApiTimestamp
   * 
   * @return eventApiTimestamp
   **/
  @JsonProperty(JSON_PROPERTY_EVENT_API_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getEventApiTimestamp() {
    return eventApiTimestamp;
  }


  @JsonProperty(JSON_PROPERTY_EVENT_API_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEventApiTimestamp(Long eventApiTimestamp) {
    this.eventApiTimestamp = eventApiTimestamp;
  }


  public Timing logTimestamp(Long logTimestamp) {
    this.logTimestamp = logTimestamp;
    return this;
  }

  /**
   * Get logTimestamp
   * 
   * @return logTimestamp
   **/
  @JsonProperty(JSON_PROPERTY_LOG_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Long getLogTimestamp() {
    return logTimestamp;
  }


  @JsonProperty(JSON_PROPERTY_LOG_TIMESTAMP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLogTimestamp(Long logTimestamp) {
    this.logTimestamp = logTimestamp;
  }


  /**
   * Return true if this Timing object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Timing timing = (Timing) o;
    return Objects.equals(this.clientLogTimestamp, timing.clientLogTimestamp)
        && Objects.equals(this.eventApiTimestamp, timing.eventApiTimestamp)
        && Objects.equals(this.logTimestamp, timing.logTimestamp);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientLogTimestamp, eventApiTimestamp, logTimestamp);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Timing {\n");
    sb.append("    clientLogTimestamp: ").append(toIndentedString(clientLogTimestamp)).append("\n");
    sb.append("    eventApiTimestamp: ").append(toIndentedString(eventApiTimestamp)).append("\n");
    sb.append("    logTimestamp: ").append(toIndentedString(logTimestamp)).append("\n");
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

