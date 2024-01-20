/*
 * 
 */
package ai.promoted.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import ai.promoted.proto.common.ClientInfo;
import ai.promoted.proto.common.Timing;
import ai.promoted.proto.common.UserInfo;

@JsonPropertyOrder({LogRequest.JSON_PROPERTY_DELIVERY_LOG,
    LogRequest.JSON_PROPERTY_COHORT_MEMBERSHIP,
    LogRequest.JSON_PROPERTY_PLATFORM_ID,
    LogRequest.JSON_PROPERTY_TIMING,
    LogRequest.JSON_PROPERTY_USER_INFO,
    LogRequest.JSON_PROPERTY_CLIENT_INFO})
public class LogRequest {
  public static final String JSON_PROPERTY_DELIVERY_LOG = "deliveryLog";
  private List<DeliveryLog> deliveryLog = null;

  public static final String JSON_PROPERTY_COHORT_MEMBERSHIP = "cohortMembership";
  private List<CohortMembership> cohortMembership = null;

  public static final String JSON_PROPERTY_CLIENT_INFO = "clientInfo";
  private ClientInfo clientInfo;

  public static final String JSON_PROPERTY_PLATFORM_ID = "platformId";
  private Integer platformId;

  public static final String JSON_PROPERTY_TIMING = "timing";
  private Timing timing;

  public static final String JSON_PROPERTY_USER_INFO = "userInfo";
  private UserInfo userInfo;

  public LogRequest deliveryLog(List<DeliveryLog> deliveryLog) {
    this.deliveryLog = deliveryLog;
    return this;
  }

  public LogRequest addDeliveryLogItem(DeliveryLog deliveryLogItem) {
    if (this.deliveryLog == null) {
      this.deliveryLog = new ArrayList<>();
    }
    this.deliveryLog.add(deliveryLogItem);
    return this;
  }

  /**
   * Get insertion
   * 
   * @return insertion
   **/
  @JsonProperty(JSON_PROPERTY_DELIVERY_LOG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<DeliveryLog> getDeliveryLog() {
    return deliveryLog;
  }

  @JsonProperty(JSON_PROPERTY_COHORT_MEMBERSHIP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCohortMembership(List<CohortMembership> cohortMembership) {
    this.cohortMembership = cohortMembership;
  }

  public LogRequest cohortMembership(List<CohortMembership> cohortMembership) {
    this.cohortMembership = cohortMembership;
    return this;
  }

  public LogRequest addCohortMembershipItem(CohortMembership cohortMembershipItem) {
    if (this.cohortMembership == null) {
      this.cohortMembership = new ArrayList<>();
    }
    this.cohortMembership.add(cohortMembershipItem);
    return this;
  }

  /**
   * Get cohortMembership
   * 
   * @return cohortMembership
   **/
  @JsonProperty(JSON_PROPERTY_COHORT_MEMBERSHIP)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<CohortMembership> getCohortMembership() {
    return cohortMembership;
  }


  @JsonProperty(JSON_PROPERTY_DELIVERY_LOG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeliveryLog(List<DeliveryLog> deliveryLog) {
    this.deliveryLog = deliveryLog;
  }

  public LogRequest clientInfo(ClientInfo clientInfo) {
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

  public LogRequest platformId(Integer platformId) {
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

  public LogRequest userInfo(UserInfo userInfo) {
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

  public LogRequest timing(Timing timing) {
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
    LogRequest request = (LogRequest) o;
    return Objects.equals(this.deliveryLog, request.deliveryLog)
        && Objects.equals(this.timing, request.timing)
        && Objects.equals(this.platformId, request.platformId)
        && Objects.equals(this.userInfo, request.userInfo)
        && Objects.equals(this.clientInfo, request.clientInfo)
        && Objects.equals(this.cohortMembership, request.cohortMembership);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deliveryLog, cohortMembership, timing, platformId, userInfo, clientInfo);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogRequest {\n");
    sb.append("    deliveryLog: ").append(toIndentedString(deliveryLog)).append("\n");
    sb.append("    timing: ").append(toIndentedString(timing)).append("\n");
    sb.append("    platformId: ").append(toIndentedString(platformId)).append("\n");
    sb.append("    userInfo: ").append(toIndentedString(userInfo)).append("\n");
    sb.append("    clientInfo: ").append(toIndentedString(clientInfo)).append("\n");
    sb.append("    cohortMembership: ").append(toIndentedString(cohortMembership)).append("\n");
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
