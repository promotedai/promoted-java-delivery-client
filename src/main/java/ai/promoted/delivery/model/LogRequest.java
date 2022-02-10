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

@JsonPropertyOrder({LogRequest.JSON_PROPERTY_DELIVERY_LOG,
    LogRequest.JSON_PROPERTY_COHORT_MEMBERSHIP})
public class LogRequest {
  public static final String JSON_PROPERTY_DELIVERY_LOG = "deliveryLog";
  private List<DeliveryLog> deliveryLog = null;

  public static final String JSON_PROPERTY_COHORT_MEMBERSHIP = "cohortMembership";
  private List<CohortMembership> cohortMembership = null;

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
        && Objects.equals(this.cohortMembership, request.cohortMembership);
  }

  @Override
  public int hashCode() {
    return Objects.hash(deliveryLog, cohortMembership);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class LogRequest {\n");
    sb.append("    deliveryLog: ").append(toIndentedString(deliveryLog)).append("\n");
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
