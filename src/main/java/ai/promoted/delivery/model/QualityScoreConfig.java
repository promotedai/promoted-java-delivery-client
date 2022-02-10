package ai.promoted.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * QualityScoreConfig
 */
@JsonPropertyOrder({QualityScoreConfig.JSON_PROPERTY_WEIGHTED_SUM_TERM})
public class QualityScoreConfig {
  public static final String JSON_PROPERTY_WEIGHTED_SUM_TERM = "weightedSumTerm";
  private List<QualityScoreTerm> weightedSumTerm = null;

  public QualityScoreConfig() {}

  public QualityScoreConfig weightedSumTerm(List<QualityScoreTerm> weightedSumTerm) {
    this.weightedSumTerm = weightedSumTerm;
    return this;
  }

  public QualityScoreConfig addWeightedSumTermItem(QualityScoreTerm weightedSumTermItem) {
    if (this.weightedSumTerm == null) {
      this.weightedSumTerm = new ArrayList<>();
    }
    this.weightedSumTerm.add(weightedSumTermItem);
    return this;
  }

  /**
   * Get weightedSumTerm
   * 
   * @return weightedSumTerm
   **/
  @JsonProperty(JSON_PROPERTY_WEIGHTED_SUM_TERM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<QualityScoreTerm> getWeightedSumTerm() {
    return weightedSumTerm;
  }


  @JsonProperty(JSON_PROPERTY_WEIGHTED_SUM_TERM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWeightedSumTerm(List<QualityScoreTerm> weightedSumTerm) {
    this.weightedSumTerm = weightedSumTerm;
  }


  /**
   * Return true if this QualityScoreConfig object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QualityScoreConfig qualityScoreConfig = (QualityScoreConfig) o;
    return Objects.equals(this.weightedSumTerm, qualityScoreConfig.weightedSumTerm);
  }

  @Override
  public int hashCode() {
    return Objects.hash(weightedSumTerm);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QualityScoreConfig {\n");
    sb.append("    weightedSumTerm: ").append(toIndentedString(weightedSumTerm)).append("\n");
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

