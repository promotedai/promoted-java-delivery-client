package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * UserInfo
 */
@JsonPropertyOrder({UserInfo.JSON_PROPERTY_IS_INTERNAL_USER, UserInfo.JSON_PROPERTY_LOG_USER_ID,
    UserInfo.JSON_PROPERTY_USER_ID})
public class UserInfo {
  public static final String JSON_PROPERTY_IS_INTERNAL_USER = "isInternalUser";
  private Boolean isInternalUser;

  public static final String JSON_PROPERTY_LOG_USER_ID = "logUserId";
  private String logUserId;

  public static final String JSON_PROPERTY_USER_ID = "userId";
  private String userId;

  public UserInfo() {}

  public UserInfo isInternalUser(Boolean isInternalUser) {
    this.isInternalUser = isInternalUser;
    return this;
  }

  /**
   * Get isInternalUser
   * 
   * @return isInternalUser
   **/
  @JsonProperty(JSON_PROPERTY_IS_INTERNAL_USER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getIsInternalUser() {
    return isInternalUser;
  }


  @JsonProperty(JSON_PROPERTY_IS_INTERNAL_USER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIsInternalUser(Boolean isInternalUser) {
    this.isInternalUser = isInternalUser;
  }


  public UserInfo logUserId(String logUserId) {
    this.logUserId = logUserId;
    return this;
  }

  /**
   * Get logUserId
   * 
   * @return logUserId
   **/
  @JsonProperty(JSON_PROPERTY_LOG_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLogUserId() {
    return logUserId;
  }


  @JsonProperty(JSON_PROPERTY_LOG_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLogUserId(String logUserId) {
    this.logUserId = logUserId;
  }


  public UserInfo userId(String userId) {
    this.userId = userId;
    return this;
  }

  /**
   * Get userId
   * 
   * @return userId
   **/
  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUserId() {
    return userId;
  }


  @JsonProperty(JSON_PROPERTY_USER_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUserId(String userId) {
    this.userId = userId;
  }


  /**
   * Return true if this UserInfo object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    UserInfo userInfo = (UserInfo) o;
    return Objects.equals(this.isInternalUser, userInfo.isInternalUser)
        && Objects.equals(this.logUserId, userInfo.logUserId)
        && Objects.equals(this.userId, userInfo.userId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(isInternalUser, logUserId, userId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class UserInfo {\n");
    sb.append("    isInternalUser: ").append(toIndentedString(isInternalUser)).append("\n");
    sb.append("    logUserId: ").append(toIndentedString(logUserId)).append("\n");
    sb.append("    userId: ").append(toIndentedString(userId)).append("\n");
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

