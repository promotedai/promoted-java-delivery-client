package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Browser
 */
@JsonPropertyOrder({Browser.JSON_PROPERTY_CLIENT_HINTS, Browser.JSON_PROPERTY_USER_AGENT,
    Browser.JSON_PROPERTY_VIEWPORT_SIZE})
public class Browser {
  public static final String JSON_PROPERTY_CLIENT_HINTS = "clientHints";
  private ClientHints clientHints;

  public static final String JSON_PROPERTY_USER_AGENT = "userAgent";
  private String userAgent;

  public static final String JSON_PROPERTY_VIEWPORT_SIZE = "viewportSize";
  private Size viewportSize;

  public Browser() {}

  public Browser clientHints(ClientHints clientHints) {
    this.clientHints = clientHints;
    return this;
  }

  /**
   * Get clientHints
   * 
   * @return clientHints
   **/
  @JsonProperty(JSON_PROPERTY_CLIENT_HINTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ClientHints getClientHints() {
    return clientHints;
  }


  @JsonProperty(JSON_PROPERTY_CLIENT_HINTS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setClientHints(ClientHints clientHints) {
    this.clientHints = clientHints;
  }


  public Browser userAgent(String userAgent) {
    this.userAgent = userAgent;
    return this;
  }

  /**
   * Get userAgent
   * 
   * @return userAgent
   **/
  @JsonProperty(JSON_PROPERTY_USER_AGENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUserAgent() {
    return userAgent;
  }


  @JsonProperty(JSON_PROPERTY_USER_AGENT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserAgent(String userAgent) {
    this.userAgent = userAgent;
  }


  public Browser viewportSize(Size viewportSize) {
    this.viewportSize = viewportSize;
    return this;
  }

  /**
   * Get viewportSize
   * 
   * @return viewportSize
   **/
  @JsonProperty(JSON_PROPERTY_VIEWPORT_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Size getViewportSize() {
    return viewportSize;
  }


  @JsonProperty(JSON_PROPERTY_VIEWPORT_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setViewportSize(Size viewportSize) {
    this.viewportSize = viewportSize;
  }


  /**
   * Return true if this Browser object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Browser browser = (Browser) o;
    return Objects.equals(this.clientHints, browser.clientHints)
        && Objects.equals(this.userAgent, browser.userAgent)
        && Objects.equals(this.viewportSize, browser.viewportSize);
  }

  @Override
  public int hashCode() {
    return Objects.hash(clientHints, userAgent, viewportSize);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Browser {\n");
    sb.append("    clientHints: ").append(toIndentedString(clientHints)).append("\n");
    sb.append("    userAgent: ").append(toIndentedString(userAgent)).append("\n");
    sb.append("    viewportSize: ").append(toIndentedString(viewportSize)).append("\n");
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

