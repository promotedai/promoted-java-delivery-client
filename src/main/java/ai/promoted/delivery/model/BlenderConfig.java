package ai.promoted.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * BlenderConfig
 */
@JsonPropertyOrder({BlenderConfig.JSON_PROPERTY_BLENDER_RULE,
    BlenderConfig.JSON_PROPERTY_QUALITY_SCORE_CONFIG})
public class BlenderConfig {
  public static final String JSON_PROPERTY_BLENDER_RULE = "blenderRule";
  private List<BlenderRule> blenderRule = null;

  public static final String JSON_PROPERTY_QUALITY_SCORE_CONFIG = "qualityScoreConfig";
  private QualityScoreConfig qualityScoreConfig;

  public BlenderConfig() {}

  public BlenderConfig blenderRule(List<BlenderRule> blenderRule) {
    this.blenderRule = blenderRule;
    return this;
  }

  public BlenderConfig addBlenderRuleItem(BlenderRule blenderRuleItem) {
    if (this.blenderRule == null) {
      this.blenderRule = new ArrayList<>();
    }
    this.blenderRule.add(blenderRuleItem);
    return this;
  }

  /**
   * Get blenderRule
   * 
   * @return blenderRule
   **/
  @JsonProperty(JSON_PROPERTY_BLENDER_RULE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<BlenderRule> getBlenderRule() {
    return blenderRule;
  }


  @JsonProperty(JSON_PROPERTY_BLENDER_RULE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBlenderRule(List<BlenderRule> blenderRule) {
    this.blenderRule = blenderRule;
  }


  public BlenderConfig qualityScoreConfig(QualityScoreConfig qualityScoreConfig) {
    this.qualityScoreConfig = qualityScoreConfig;
    return this;
  }

  /**
   * Get qualityScoreConfig
   * 
   * @return qualityScoreConfig
   **/
  @JsonProperty(JSON_PROPERTY_QUALITY_SCORE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public QualityScoreConfig getQualityScoreConfig() {
    return qualityScoreConfig;
  }


  @JsonProperty(JSON_PROPERTY_QUALITY_SCORE_CONFIG)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setQualityScoreConfig(QualityScoreConfig qualityScoreConfig) {
    this.qualityScoreConfig = qualityScoreConfig;
  }


  /**
   * Return true if this BlenderConfig object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    BlenderConfig blenderConfig = (BlenderConfig) o;
    return Objects.equals(this.blenderRule, blenderConfig.blenderRule)
        && Objects.equals(this.qualityScoreConfig, blenderConfig.qualityScoreConfig);
  }

  @Override
  public int hashCode() {
    return Objects.hash(blenderRule, qualityScoreConfig);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class BlenderConfig {\n");
    sb.append("    blenderRule: ").append(toIndentedString(blenderRule)).append("\n");
    sb.append("    qualityScoreConfig: ").append(toIndentedString(qualityScoreConfig)).append("\n");
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

