package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * BlenderRule
 */
@JsonPropertyOrder({BlenderRule.JSON_PROPERTY_EVAL_METHOD, BlenderRule.JSON_PROPERTY_RULE,
    BlenderRule.JSON_PROPERTY_ATTRIBUTE_NAME, BlenderRule.JSON_PROPERTY_HASHED_ATTRIBUTE})
public class BlenderRule {
  public static final String JSON_PROPERTY_EVAL_METHOD = "EvalMethod";
  private Object evalMethod;

  public static final String JSON_PROPERTY_RULE = "Rule";
  private Object rule;

  public static final String JSON_PROPERTY_ATTRIBUTE_NAME = "attributeName";
  private String attributeName;

  public static final String JSON_PROPERTY_HASHED_ATTRIBUTE = "hashedAttribute";
  private Integer hashedAttribute;

  public BlenderRule() {}

  public BlenderRule evalMethod(Object evalMethod) {
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


  public BlenderRule rule(Object rule) {
    this.rule = rule;
    return this;
  }

  /**
   * Get rule
   * 
   * @return rule
   **/
  @JsonProperty(JSON_PROPERTY_RULE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Object getRule() {
    return rule;
  }


  @JsonProperty(JSON_PROPERTY_RULE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRule(Object rule) {
    this.rule = rule;
  }


  public BlenderRule attributeName(String attributeName) {
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


  public BlenderRule hashedAttribute(Integer hashedAttribute) {
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


  /**
   * Return true if this BlenderRule object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BlenderRule blenderRule = (BlenderRule) o;
    return Objects.equals(this.evalMethod, blenderRule.evalMethod)
        && Objects.equals(this.rule, blenderRule.rule)
        && Objects.equals(this.attributeName, blenderRule.attributeName)
        && Objects.equals(this.hashedAttribute, blenderRule.hashedAttribute);
  }

  @Override
  public int hashCode() {
    return Objects.hash(evalMethod, rule, attributeName, hashedAttribute);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BlenderRule {\n");
    sb.append("    evalMethod: ").append(toIndentedString(evalMethod)).append("\n");
    sb.append("    rule: ").append(toIndentedString(rule)).append("\n");
    sb.append("    attributeName: ").append(toIndentedString(attributeName)).append("\n");
    sb.append("    hashedAttribute: ").append(toIndentedString(hashedAttribute)).append("\n");
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

