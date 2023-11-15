package ai.promoted.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Request
 */
@JsonPropertyOrder({Request.JSON_PROPERTY_AUTO_VIEW_ID, Request.JSON_PROPERTY_BLENDER_CONFIG,
    Request.JSON_PROPERTY_CLIENT_INFO, Request.JSON_PROPERTY_CLIENT_REQUEST_ID,
    Request.JSON_PROPERTY_DEBUG, Request.JSON_PROPERTY_DEVICE,
    Request.JSON_PROPERTY_DISABLE_PERSONALIZATION, Request.JSON_PROPERTY_INSERTION,
    Request.JSON_PROPERTY_PAGING, Request.JSON_PROPERTY_PLATFORM_ID,
    Request.JSON_PROPERTY_PROPERTIES, Request.JSON_PROPERTY_REQUEST_ID,
    Request.JSON_PROPERTY_SEARCH_QUERY, Request.JSON_PROPERTY_SESSION_ID,
    Request.JSON_PROPERTY_TIMING, Request.JSON_PROPERTY_USE_CASE, Request.JSON_PROPERTY_USER_INFO,
    Request.JSON_PROPERTY_VIEW_ID})
public class Request implements Cloneable {
  public static final String JSON_PROPERTY_AUTO_VIEW_ID = "autoViewId";
  private String autoViewId;

  public static final String JSON_PROPERTY_BLENDER_CONFIG = "blenderConfig";
  private BlenderConfig blenderConfig;

  public static final String JSON_PROPERTY_CLIENT_INFO = "clientInfo";
  private ClientInfo clientInfo;

  public static final String JSON_PROPERTY_CLIENT_REQUEST_ID = "clientRequestId";
  private String clientRequestId;

  public static final String JSON_PROPERTY_DEBUG = "debug";
  private Boolean debug;

  public static final String JSON_PROPERTY_DEVICE = "device";
  private Device device;

  public static final String JSON_PROPERTY_DISABLE_PERSONALIZATION = "disablePersonalization";
  private Boolean disablePersonalization;

  public static final String JSON_PROPERTY_INSERTION = "insertion";
  private List<Insertion> insertion = null;

  public static final String JSON_PROPERTY_PAGING = "paging";
  private Paging paging;

  public static final String JSON_PROPERTY_PLATFORM_ID = "platformId";
  private Integer platformId;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  private Properties properties;

  public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
  private String requestId;

  public static final String JSON_PROPERTY_SEARCH_QUERY = "searchQuery";
  private String searchQuery;

  public static final String JSON_PROPERTY_SESSION_ID = "sessionId";
  private String sessionId;

  public static final String JSON_PROPERTY_TIMING = "timing";
  private Timing timing;

  public static final String JSON_PROPERTY_USE_CASE = "useCase";
  private UseCase useCase;

  public static final String JSON_PROPERTY_USER_INFO = "userInfo";
  private UserInfo userInfo;

  public static final String JSON_PROPERTY_VIEW_ID = "viewId";
  private String viewId;

  public Request() {}

  public Request autoViewId(String autoViewId) {
    this.autoViewId = autoViewId;
    return this;
  }

  /**
   * Get autoViewId
   * 
   * @return autoViewId
   **/
  @JsonProperty(JSON_PROPERTY_AUTO_VIEW_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getAutoViewId() {
    return autoViewId;
  }


  @JsonProperty(JSON_PROPERTY_AUTO_VIEW_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAutoViewId(String autoViewId) {
    this.autoViewId = autoViewId;
  }


  public Request blenderConfig(BlenderConfig blenderConfig) {
    this.blenderConfig = blenderConfig;
    return this;
  }

  /**
   * Get blenderConfig
   * 
   * @return blenderConfig
   **/
  @JsonProperty(JSON_PROPERTY_BLENDER_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public BlenderConfig getBlenderConfig() {
    return blenderConfig;
  }


  @JsonProperty(JSON_PROPERTY_BLENDER_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBlenderConfig(BlenderConfig blenderConfig) {
    this.blenderConfig = blenderConfig;
  }


  public Request clientInfo(ClientInfo clientInfo) {
    this.clientInfo = clientInfo;
    return this;
  }

  /**
   * Get clientInfo
   * 
   * @return clientInfo
   **/
  @JsonProperty(JSON_PROPERTY_CLIENT_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public ClientInfo getClientInfo() {
    return clientInfo;
  }


  @JsonProperty(JSON_PROPERTY_CLIENT_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setClientInfo(ClientInfo clientInfo) {
    this.clientInfo = clientInfo;
  }


  public Request clientRequestId(String clientRequestId) {
    this.clientRequestId = clientRequestId;
    return this;
  }

  /**
   * Get clientRequestId
   * 
   * @return clientRequestId
   **/
  @JsonProperty(JSON_PROPERTY_CLIENT_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getClientRequestId() {
    return clientRequestId;
  }


  @JsonProperty(JSON_PROPERTY_CLIENT_REQUEST_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setClientRequestId(String clientRequestId) {
    this.clientRequestId = clientRequestId;
  }


  public Request debug(Boolean debug) {
    this.debug = debug;
    return this;
  }

  /**
   * Get debug
   * 
   * @return debug
   **/
  @JsonProperty(JSON_PROPERTY_DEBUG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getDebug() {
    return debug;
  }


  @JsonProperty(JSON_PROPERTY_DEBUG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDebug(Boolean debug) {
    this.debug = debug;
  }


  public Request device(Device device) {
    this.device = device;
    return this;
  }

  /**
   * Get device
   * 
   * @return device
   **/
  @JsonProperty(JSON_PROPERTY_DEVICE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Device getDevice() {
    return device;
  }


  @JsonProperty(JSON_PROPERTY_DEVICE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDevice(Device device) {
    this.device = device;
  }


  public Request disablePersonalization(Boolean disablePersonalization) {
    this.disablePersonalization = disablePersonalization;
    return this;
  }

  /**
   * Get disablePersonalization
   * 
   * @return disablePersonalization
   **/
  @JsonProperty(JSON_PROPERTY_DISABLE_PERSONALIZATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getDisablePersonalization() {
    return disablePersonalization;
  }


  @JsonProperty(JSON_PROPERTY_DISABLE_PERSONALIZATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDisablePersonalization(Boolean disablePersonalization) {
    this.disablePersonalization = disablePersonalization;
  }


  public Request insertion(List<Insertion> insertion) {
    this.insertion = insertion;
    return this;
  }

  public Request addInsertionItem(Insertion insertionItem) {
    if (this.insertion == null) {
      this.insertion = new ArrayList<>();
    }
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


  public Request paging(Paging paging) {
    this.paging = paging;
    return this;
  }

  /**
   * Get paging
   * 
   * @return paging
   **/
  @JsonProperty(JSON_PROPERTY_PAGING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Paging getPaging() {
    return paging;
  }


  @JsonProperty(JSON_PROPERTY_PAGING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPaging(Paging paging) {
    this.paging = paging;
  }


  public Request platformId(Integer platformId) {
    this.platformId = platformId;
    return this;
  }

  /**
   * Get platformId
   * 
   * @return platformId
   **/
  @JsonProperty(JSON_PROPERTY_PLATFORM_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getPlatformId() {
    return platformId;
  }


  @JsonProperty(JSON_PROPERTY_PLATFORM_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPlatformId(Integer platformId) {
    this.platformId = platformId;
  }


  public Request properties(Properties properties) {
    this.properties = properties;
    return this;
  }

  /**
   * Get properties
   * 
   * @return properties
   **/
  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Properties getProperties() {
    return properties;
  }


  @JsonProperty(JSON_PROPERTY_PROPERTIES)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setProperties(Properties properties) {
    this.properties = properties;
  }


  public Request requestId(String requestId) {
    this.requestId = requestId;
    return this;
  }

  /**
   * Get requestId
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


  public Request searchQuery(String searchQuery) {
    this.searchQuery = searchQuery;
    return this;
  }

  /**
   * Get searchQuery
   * 
   * @return searchQuery
   **/
  @JsonProperty(JSON_PROPERTY_SEARCH_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSearchQuery() {
    return searchQuery;
  }


  @JsonProperty(JSON_PROPERTY_SEARCH_QUERY)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSearchQuery(String searchQuery) {
    this.searchQuery = searchQuery;
  }


  public Request sessionId(String sessionId) {
    this.sessionId = sessionId;
    return this;
  }

  /**
   * Get sessionId
   * 
   * @return sessionId
   **/
  @JsonProperty(JSON_PROPERTY_SESSION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getSessionId() {
    return sessionId;
  }


  @JsonProperty(JSON_PROPERTY_SESSION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSessionId(String sessionId) {
    this.sessionId = sessionId;
  }


  public Request timing(Timing timing) {
    this.timing = timing;
    return this;
  }

  /**
   * Get timing
   * 
   * @return timing
   **/
  @JsonProperty(JSON_PROPERTY_TIMING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Timing getTiming() {
    return timing;
  }


  @JsonProperty(JSON_PROPERTY_TIMING)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTiming(Timing timing) {
    this.timing = timing;
  }


  public Request useCase(UseCase useCase) {
    this.useCase = useCase;
    return this;
  }

  /**
   * Get useCase
   * 
   * @return useCase
   **/
  @JsonProperty(JSON_PROPERTY_USE_CASE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public UseCase getUseCase() {
    return useCase;
  }


  @JsonProperty(JSON_PROPERTY_USE_CASE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUseCase(UseCase useCase) {
    this.useCase = useCase;
  }


  public Request userInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
    return this;
  }

  /**
   * Get userInfo
   * 
   * @return userInfo
   **/
  @JsonProperty(JSON_PROPERTY_USER_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public UserInfo getUserInfo() {
    return userInfo;
  }


  @JsonProperty(JSON_PROPERTY_USER_INFO)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserInfo(UserInfo userInfo) {
    this.userInfo = userInfo;
  }


  public Request viewId(String viewId) {
    this.viewId = viewId;
    return this;
  }

  /**
   * Get viewId
   * 
   * @return viewId
   **/
  @JsonProperty(JSON_PROPERTY_VIEW_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getViewId() {
    return viewId;
  }


  @JsonProperty(JSON_PROPERTY_VIEW_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setViewId(String viewId) {
    this.viewId = viewId;
  }


  /**
   * Return true if this Request object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Request request = (Request) o;
    return Objects.equals(this.autoViewId, request.autoViewId)
        && Objects.equals(this.blenderConfig, request.blenderConfig)
        && Objects.equals(this.clientInfo, request.clientInfo)
        && Objects.equals(this.clientRequestId, request.clientRequestId)
        && Objects.equals(this.debug, request.debug)
        && Objects.equals(this.device, request.device)
        && Objects.equals(this.disablePersonalization, request.disablePersonalization)
        && Objects.equals(this.insertion, request.insertion)
        && Objects.equals(this.paging, request.paging)
        && Objects.equals(this.platformId, request.platformId)
        && Objects.equals(this.properties, request.properties)
        && Objects.equals(this.requestId, request.requestId)
        && Objects.equals(this.searchQuery, request.searchQuery)
        && Objects.equals(this.sessionId, request.sessionId)
        && Objects.equals(this.timing, request.timing)
        && Objects.equals(this.useCase, request.useCase)
        && Objects.equals(this.userInfo, request.userInfo)
        && Objects.equals(this.viewId, request.viewId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(autoViewId, blenderConfig, clientInfo, clientRequestId, debug,
        device, disablePersonalization, insertion, paging, platformId, properties,
        requestId, searchQuery, sessionId, timing, useCase, userInfo, viewId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Request {\n");
    sb.append("    autoViewId: ").append(toIndentedString(autoViewId)).append("\n");
    sb.append("    blenderConfig: ").append(toIndentedString(blenderConfig)).append("\n");
    sb.append("    clientInfo: ").append(toIndentedString(clientInfo)).append("\n");
    sb.append("    clientRequestId: ").append(toIndentedString(clientRequestId)).append("\n");
    sb.append("    debug: ").append(toIndentedString(debug)).append("\n");
    sb.append("    disablePersonalization: ").append(toIndentedString(disablePersonalization)).append("\n");
    sb.append("    device: ").append(toIndentedString(device)).append("\n");
    sb.append("    insertion: ").append(toIndentedString(insertion)).append("\n");
    sb.append("    paging: ").append(toIndentedString(paging)).append("\n");
    sb.append("    platformId: ").append(toIndentedString(platformId)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    searchQuery: ").append(toIndentedString(searchQuery)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    timing: ").append(toIndentedString(timing)).append("\n");
    sb.append("    useCase: ").append(toIndentedString(useCase)).append("\n");
    sb.append("    userInfo: ").append(toIndentedString(userInfo)).append("\n");
    sb.append("    viewId: ").append(toIndentedString(viewId)).append("\n");
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
   * Gets a mostly-shallow copy of the Request, but safely modifiable ClientInfo.
   */
  public Request clone() throws CloneNotSupportedException {
    Request requestCopy = (Request) super.clone();
    requestCopy.setClientInfo(clientInfo.clone());
    return requestCopy;
  }
}

