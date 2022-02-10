package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * TermConditionalEvaluation
 */
@JsonPropertyOrder({TermConditionalEvaluation.JSON_PROPERTY_EVAL_METHOD,
    TermConditionalEvaluation.JSON_PROPERTY_ATTRIBUTE_NAME,
    TermConditionalEvaluation.JSON_PROPERTY_HASHED_ATTRIBUTE,
    TermConditionalEvaluation.JSON_PROPERTY_VALUE_IF_FALSE})
public class TermConditionalEvaluation {
  public static final String JSON_PROPERTY_EVAL_METHOD = "EvalMethod";
  private Object evalMethod;

  public static final String JSON_PROPERTY_ATTRIBUTE_NAME = "attributeName";
  private String attributeName;

  public static final String JSON_PROPERTY_HASHED_ATTRIBUTE = "hashedAttribute";
  private Integer hashedAttribute;

  public static final String JSON_PROPERTY_VALUE_IF_FALSE = "valueIfFalse";
  private Float valueIfFalse;

  public TermConditionalEvaluation() {}

  public TermConditionalEvaluation evalMethod(Object evalMethod) {
    this.evalMethod = evalMethod;
    return this;
  }

  /**
   * Get evalMethod
   * 
   * @return evalMethod
   **/
  @JsonProperty(JSON_PROPERTY_EVAL_METHOD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getEvalMethod() {
    return evalMethod;
  }


  @JsonProperty(JSON_PROPERTY_EVAL_METHOD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setEvalMethod(Object evalMethod) {
    this.evalMethod = evalMethod;
  }


  public TermConditionalEvaluation attributeName(String attributeName) {
    this.attributeName = attributeName;
    return this;
  }

  /**
   * Get attributeName
   * 
   * @return attributeName
   **/
  @JsonProperty(JSON_PROPERTY_ATTRIBUTE_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getAttributeName() {
    return attributeName;
  }


  @JsonProperty(JSON_PROPERTY_ATTRIBUTE_NAME)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAttributeName(String attributeName) {
    this.attributeName = attributeName;
  }


  public TermConditionalEvaluation hashedAttribute(Integer hashedAttribute) {
    this.hashedAttribute = hashedAttribute;
    return this;
  }

  /**
   * Get hashedAttribute
   * 
   * @return hashedAttribute
   **/
  @JsonProperty(JSON_PROPERTY_HASHED_ATTRIBUTE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getHashedAttribute() {
    return hashedAttribute;
  }


  @JsonProperty(JSON_PROPERTY_HASHED_ATTRIBUTE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setHashedAttribute(Integer hashedAttribute) {
    this.hashedAttribute = hashedAttribute;
  }


  public TermConditionalEvaluation valueIfFalse(Float valueIfFalse) {
    this.valueIfFalse = valueIfFalse;
    return this;
  }

  /**
   * Get valueIfFalse
   * 
   * @return valueIfFalse
   **/
  @JsonProperty(JSON_PROPERTY_VALUE_IF_FALSE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Float getValueIfFalse() {
    return valueIfFalse;
  }


  @JsonProperty(JSON_PROPERTY_VALUE_IF_FALSE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setValueIfFalse(Float valueIfFalse) {
    this.valueIfFalse = valueIfFalse;
  }


  /**
   * Return true if this TermConditionalEvaluation object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    TermConditionalEvaluation termConditionalEvaluation = (TermConditionalEvaluation) o;
    return Objects.equals(this.evalMethod, termConditionalEvaluation.evalMethod)
        && Objects.equals(this.attributeName, termConditionalEvaluation.attributeName)
        && Objects.equals(this.hashedAttribute, termConditionalEvaluation.hashedAttribute)
        && Objects.equals(this.valueIfFalse, termConditionalEvaluation.valueIfFalse);
  }

  @Override
  public int hashCode() {
    return Objects.hash(evalMethod, attributeName, hashedAttribute, valueIfFalse);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class TermConditionalEvaluation {\n");
    sb.append("    evalMethod: ").append(toIndentedString(evalMethod)).append("\n");
    sb.append("    attributeName: ").append(toIndentedString(attributeName)).append("\n");
    sb.append("    hashedAttribute: ").append(toIndentedString(hashedAttribute)).append("\n");
    sb.append("    valueIfFalse: ").append(toIndentedString(valueIfFalse)).append("\n");
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

