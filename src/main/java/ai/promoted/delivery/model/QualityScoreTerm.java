package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * QualityScoreTerm
 */
@JsonPropertyOrder({QualityScoreTerm.JSON_PROPERTY_FETCH_METHOD,
    QualityScoreTerm.JSON_PROPERTY_FETCH_HIGH, QualityScoreTerm.JSON_PROPERTY_FETCH_LOW,
    QualityScoreTerm.JSON_PROPERTY_OFFSET,
    QualityScoreTerm.JSON_PROPERTY_TERM_CONDITIONAL_EVALUATION,
    QualityScoreTerm.JSON_PROPERTY_WEIGHT})
public class QualityScoreTerm {
  public static final String JSON_PROPERTY_FETCH_METHOD = "FetchMethod";
  private Object fetchMethod;

  public static final String JSON_PROPERTY_FETCH_HIGH = "fetchHigh";
  private Float fetchHigh;

  public static final String JSON_PROPERTY_FETCH_LOW = "fetchLow";
  private Float fetchLow;

  public static final String JSON_PROPERTY_OFFSET = "offset";
  private Float offset;

  public static final String JSON_PROPERTY_TERM_CONDITIONAL_EVALUATION =
      "termConditionalEvaluation";
  private TermConditionalEvaluation termConditionalEvaluation;

  public static final String JSON_PROPERTY_WEIGHT = "weight";
  private Float weight;

  public QualityScoreTerm() {}

  public QualityScoreTerm fetchMethod(Object fetchMethod) {
    this.fetchMethod = fetchMethod;
    return this;
  }

  /**
   * Get fetchMethod
   * 
   * @return fetchMethod
   **/
  @JsonProperty(JSON_PROPERTY_FETCH_METHOD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getFetchMethod() {
    return fetchMethod;
  }


  @JsonProperty(JSON_PROPERTY_FETCH_METHOD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFetchMethod(Object fetchMethod) {
    this.fetchMethod = fetchMethod;
  }


  public QualityScoreTerm fetchHigh(Float fetchHigh) {
    this.fetchHigh = fetchHigh;
    return this;
  }

  /**
   * Get fetchHigh
   * 
   * @return fetchHigh
   **/
  @JsonProperty(JSON_PROPERTY_FETCH_HIGH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Float getFetchHigh() {
    return fetchHigh;
  }


  @JsonProperty(JSON_PROPERTY_FETCH_HIGH)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFetchHigh(Float fetchHigh) {
    this.fetchHigh = fetchHigh;
  }


  public QualityScoreTerm fetchLow(Float fetchLow) {
    this.fetchLow = fetchLow;
    return this;
  }

  /**
   * Get fetchLow
   * 
   * @return fetchLow
   **/
  @JsonProperty(JSON_PROPERTY_FETCH_LOW)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Float getFetchLow() {
    return fetchLow;
  }


  @JsonProperty(JSON_PROPERTY_FETCH_LOW)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFetchLow(Float fetchLow) {
    this.fetchLow = fetchLow;
  }


  public QualityScoreTerm offset(Float offset) {
    this.offset = offset;
    return this;
  }

  /**
   * Get offset
   * 
   * @return offset
   **/
  @JsonProperty(JSON_PROPERTY_OFFSET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Float getOffset() {
    return offset;
  }


  @JsonProperty(JSON_PROPERTY_OFFSET)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOffset(Float offset) {
    this.offset = offset;
  }


  public QualityScoreTerm termConditionalEvaluation(
      TermConditionalEvaluation termConditionalEvaluation) {
    this.termConditionalEvaluation = termConditionalEvaluation;
    return this;
  }

  /**
   * Get termConditionalEvaluation
   * 
   * @return termConditionalEvaluation
   **/
  @JsonProperty(JSON_PROPERTY_TERM_CONDITIONAL_EVALUATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public TermConditionalEvaluation getTermConditionalEvaluation() {
    return termConditionalEvaluation;
  }


  @JsonProperty(JSON_PROPERTY_TERM_CONDITIONAL_EVALUATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setTermConditionalEvaluation(TermConditionalEvaluation termConditionalEvaluation) {
    this.termConditionalEvaluation = termConditionalEvaluation;
  }


  public QualityScoreTerm weight(Float weight) {
    this.weight = weight;
    return this;
  }

  /**
   * Get weight
   * 
   * @return weight
   **/
  @JsonProperty(JSON_PROPERTY_WEIGHT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Float getWeight() {
    return weight;
  }


  @JsonProperty(JSON_PROPERTY_WEIGHT)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setWeight(Float weight) {
    this.weight = weight;
  }


  /**
   * Return true if this QualityScoreTerm object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    QualityScoreTerm qualityScoreTerm = (QualityScoreTerm) o;
    return Objects.equals(this.fetchMethod, qualityScoreTerm.fetchMethod)
        && Objects.equals(this.fetchHigh, qualityScoreTerm.fetchHigh)
        && Objects.equals(this.fetchLow, qualityScoreTerm.fetchLow)
        && Objects.equals(this.offset, qualityScoreTerm.offset)
        && Objects.equals(this.termConditionalEvaluation,
            qualityScoreTerm.termConditionalEvaluation)
        && Objects.equals(this.weight, qualityScoreTerm.weight);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fetchMethod, fetchHigh, fetchLow, offset, termConditionalEvaluation,
        weight);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class QualityScoreTerm {\n");
    sb.append("    fetchMethod: ").append(toIndentedString(fetchMethod)).append("\n");
    sb.append("    fetchHigh: ").append(toIndentedString(fetchHigh)).append("\n");
    sb.append("    fetchLow: ").append(toIndentedString(fetchLow)).append("\n");
    sb.append("    offset: ").append(toIndentedString(offset)).append("\n");
    sb.append("    termConditionalEvaluation: ").append(toIndentedString(termConditionalEvaluation))
        .append("\n");
    sb.append("    weight: ").append(toIndentedString(weight)).append("\n");
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

