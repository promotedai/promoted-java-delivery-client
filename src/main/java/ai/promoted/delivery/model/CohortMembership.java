package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

@JsonPropertyOrder({CohortMembership.JSON_PROPERTY_COHORT_ID, CohortMembership.JSON_PROPERTY_ARM})
public class CohortMembership {
  public static final String JSON_PROPERTY_COHORT_ID = "cohortId";
  private String cohortId;

  public static final String JSON_PROPERTY_ARM = "arm";
  private CohortArm arm;

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
    return Objects.equals(this.arm, mem.arm) && Objects.equals(this.cohortId, mem.cohortId);
  }

  @Override
  public int hashCode() {
    return Objects.hash(arm, cohortId);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class CohortMembership {\n");
    sb.append("    cohortId: ").append(toIndentedString(cohortId)).append("\n");
    sb.append("    arm: ").append(toIndentedString(arm)).append("\n");
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
