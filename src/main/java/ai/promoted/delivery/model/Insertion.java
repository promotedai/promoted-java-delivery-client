package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Insertion
 */
@JsonPropertyOrder({Insertion.JSON_PROPERTY_AUTO_VIEW_ID, Insertion.JSON_PROPERTY_CLIENT_INFO,
    Insertion.JSON_PROPERTY_CONTENT_ID, Insertion.JSON_PROPERTY_INSERTION_ID,
    Insertion.JSON_PROPERTY_PLATFORM_ID, Insertion.JSON_PROPERTY_POSITION,
    Insertion.JSON_PROPERTY_PROPERTIES, Insertion.JSON_PROPERTY_REQUEST_ID,
    Insertion.JSON_PROPERTY_RETRIEVAL_RANK, Insertion.JSON_PROPERTY_RETRIEVAL_SCORE,
    Insertion.JSON_PROPERTY_SESSION_ID, Insertion.JSON_PROPERTY_TIMING,
    Insertion.JSON_PROPERTY_USER_INFO, Insertion.JSON_PROPERTY_VIEW_ID})
public class Insertion {
  public static final String JSON_PROPERTY_AUTO_VIEW_ID = "autoViewId";
  private String autoViewId;

  public static final String JSON_PROPERTY_CLIENT_INFO = "clientInfo";
  private ClientInfo clientInfo;

  public static final String JSON_PROPERTY_CONTENT_ID = "contentId";
  private String contentId;

  public static final String JSON_PROPERTY_INSERTION_ID = "insertionId";
  private String insertionId;

  public static final String JSON_PROPERTY_PLATFORM_ID = "platformId";
  private Integer platformId;

  public static final String JSON_PROPERTY_POSITION = "position";
  private Integer position;

  public static final String JSON_PROPERTY_PROPERTIES = "properties";
  private Properties properties;

  public static final String JSON_PROPERTY_REQUEST_ID = "requestId";
  private String requestId;

  public static final String JSON_PROPERTY_RETRIEVAL_RANK = "retrievalRank";
  private Integer retrievalRank;

  public static final String JSON_PROPERTY_RETRIEVAL_SCORE = "retrievalScore";
  private Float retrievalScore;

  public static final String JSON_PROPERTY_SESSION_ID = "sessionId";
  private String sessionId;

  public static final String JSON_PROPERTY_TIMING = "timing";
  private Timing timing;

  public static final String JSON_PROPERTY_USER_INFO = "userInfo";
  private UserInfo userInfo;

  public static final String JSON_PROPERTY_VIEW_ID = "viewId";
  private String viewId;

  public Insertion() {}

  public Insertion autoViewId(String autoViewId) {
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


  public Insertion clientInfo(ClientInfo clientInfo) {
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


  public Insertion contentId(String contentId) {
    this.contentId = contentId;
    return this;
  }

  /**
   * Get contentId
   * 
   * @return contentId
   **/
  @JsonProperty(JSON_PROPERTY_CONTENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getContentId() {
    return contentId;
  }


  @JsonProperty(JSON_PROPERTY_CONTENT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setContentId(String contentId) {
    this.contentId = contentId;
  }

  public Insertion insertionId(String insertionId) {
    this.insertionId = insertionId;
    return this;
  }

  /**
   * Get insertionId
   * 
   * @return insertionId
   **/
  @JsonProperty(JSON_PROPERTY_INSERTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getInsertionId() {
    return insertionId;
  }


  @JsonProperty(JSON_PROPERTY_INSERTION_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setInsertionId(String insertionId) {
    this.insertionId = insertionId;
  }

  public Insertion platformId(Integer platformId) {
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


  public Insertion position(Integer position) {
    this.position = position;
    return this;
  }

  /**
   * Get position
   * 
   * @return position
   **/
  @JsonProperty(JSON_PROPERTY_POSITION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getPosition() {
    return position;
  }


  @JsonProperty(JSON_PROPERTY_POSITION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPosition(Integer position) {
    this.position = position;
  }

  public Insertion properties(Properties properties) {
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


  public Insertion requestId(String requestId) {
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


  public Insertion retrievalRank(Integer retrievalRank) {
    this.retrievalRank = retrievalRank;
    return this;
  }

  /**
   * Get retrievalRank
   * 
   * @return retrievalRank
   **/
  @JsonProperty(JSON_PROPERTY_RETRIEVAL_RANK)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getRetrievalRank() {
    return retrievalRank;
  }


  @JsonProperty(JSON_PROPERTY_RETRIEVAL_RANK)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRetrievalRank(Integer retrievalRank) {
    this.retrievalRank = retrievalRank;
  }


  public Insertion retrievalScore(Float retrievalScore) {
    this.retrievalScore = retrievalScore;
    return this;
  }

  /**
   * Get retrievalScore
   * 
   * @return retrievalScore
   **/
  @JsonProperty(JSON_PROPERTY_RETRIEVAL_SCORE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Float getRetrievalScore() {
    return retrievalScore;
  }


  @JsonProperty(JSON_PROPERTY_RETRIEVAL_SCORE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRetrievalScore(Float retrievalScore) {
    this.retrievalScore = retrievalScore;
  }


  public Insertion sessionId(String sessionId) {
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


  public Insertion timing(Timing timing) {
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


  public Insertion userInfo(UserInfo userInfo) {
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


  public Insertion viewId(String viewId) {
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
   * Return true if this Insertion object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Insertion insertion = (Insertion) o;
    return Objects.equals(this.autoViewId, insertion.autoViewId)
        && Objects.equals(this.clientInfo, insertion.clientInfo)
        && Objects.equals(this.contentId, insertion.contentId)
        && Objects.equals(this.insertionId, insertion.insertionId)
        && Objects.equals(this.platformId, insertion.platformId)
        && Objects.equals(this.position, insertion.position)
        && Objects.equals(this.properties, insertion.properties)
        && Objects.equals(this.requestId, insertion.requestId)
        && Objects.equals(this.retrievalRank, insertion.retrievalRank)
        && Objects.equals(this.retrievalScore, insertion.retrievalScore)
        && Objects.equals(this.sessionId, insertion.sessionId)
        && Objects.equals(this.timing, insertion.timing)
        && Objects.equals(this.userInfo, insertion.userInfo)
        && Objects.equals(this.viewId, insertion.viewId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(autoViewId, clientInfo, contentId, insertionId, platformId, position,
        properties, requestId, retrievalRank, retrievalScore, sessionId, timing, userInfo, viewId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Insertion {\n");
    sb.append("    autoViewId: ").append(toIndentedString(autoViewId)).append("\n");
    sb.append("    clientInfo: ").append(toIndentedString(clientInfo)).append("\n");
    sb.append("    contentId: ").append(toIndentedString(contentId)).append("\n");
    sb.append("    insertionId: ").append(toIndentedString(insertionId)).append("\n");
    sb.append("    platformId: ").append(toIndentedString(platformId)).append("\n");
    sb.append("    position: ").append(toIndentedString(position)).append("\n");
    sb.append("    properties: ").append(toIndentedString(properties)).append("\n");
    sb.append("    requestId: ").append(toIndentedString(requestId)).append("\n");
    sb.append("    retrievalRank: ").append(toIndentedString(retrievalRank)).append("\n");
    sb.append("    retrievalScore: ").append(toIndentedString(retrievalScore)).append("\n");
    sb.append("    sessionId: ").append(toIndentedString(sessionId)).append("\n");
    sb.append("    timing: ").append(toIndentedString(timing)).append("\n");
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

}

