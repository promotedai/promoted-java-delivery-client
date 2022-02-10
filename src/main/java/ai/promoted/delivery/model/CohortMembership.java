package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({CohortMembership.JSON_PROPERTY_COHORT_ID, CohortMembership.JSON_PROPERTY_ARM,
    CohortMembership.JSON_PROPERTY_USER_INFO, CohortMembership.JSON_PROPERTY_TIMING,
    CohortMembership.JSON_PROPERTY_PLATFORM_ID})
public class CohortMembership {
  public static final String JSON_PROPERTY_COHORT_ID = "cohortId";
  private String cohortId;

  public static final String JSON_PROPERTY_ARM = "arm";
  private CohortArm arm;

  public static final String JSON_PROPERTY_PLATFORM_ID = "platformId";
  private Integer platformId;

  public static final String JSON_PROPERTY_USER_INFO = "userInfo";
  private UserInfo userInfo;

  public static final String JSON_PROPERTY_TIMING = "timing";
  private Timing timing;

  public CohortMembership cohortId(String cohortId) {
    this.cohortId = cohortId;
    return this;
  }

  /**
   * Get cohortId
   * 
   * @return cohortId
   **/
  @JsonProperty(JSON_PROPERTY_COHORT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getCohortId() {
    return cohortId;
  }


  @JsonProperty(JSON_PROPERTY_COHORT_ID)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setCohortId(String cohortId) {
    this.cohortId = cohortId;
  }


  public CohortMembership arm(CohortArm arm) {
    this.arm = arm;
    return this;
  }

  /**
   * Get cohortId
   * 
   * @return cohortId
   **/
  @JsonProperty(JSON_PROPERTY_ARM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public CohortArm getArm() {
    return arm;
  }


  @JsonProperty(JSON_PROPERTY_ARM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setArm(CohortArm arm) {
    this.arm = arm;
  }

  public CohortMembership platformId(Integer platformId) {
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

  public CohortMembership userInfo(UserInfo userInfo) {
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

  public CohortMembership timing(Timing timing) {
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
    CohortMembership mem = (CohortMembership) o;
    return Objects.equals(this.arm, mem.arm) && Objects.equals(this.cohortId, mem.cohortId)
        && Objects.equals(this.userInfo, mem.userInfo) && Objects.equals(this.timing, mem.timing)
        && Objects.equals(this.platformId, mem.platformId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arm, cohortId, userInfo, timing, platformId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CohortMembership {\n");
    sb.append("    cohortId: ").append(toIndentedString(cohortId)).append("\n");
    sb.append("    arm: ").append(toIndentedString(arm)).append("\n");
    sb.append("    userInfo: ").append(toIndentedString(userInfo)).append("\n");
    sb.append("    timing: ").append(toIndentedString(timing)).append("\n");
    sb.append("    platformId: ").append(toIndentedString(platformId)).append("\n");
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
