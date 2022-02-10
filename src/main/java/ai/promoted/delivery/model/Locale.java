package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Locale
 */
@JsonPropertyOrder({Locale.JSON_PROPERTY_LANGUAGE_CODE, Locale.JSON_PROPERTY_REGION_CODE})
public class Locale {
  public static final String JSON_PROPERTY_LANGUAGE_CODE = "languageCode";
  private String languageCode;

  public static final String JSON_PROPERTY_REGION_CODE = "regionCode";
  private String regionCode;

  public Locale() {}

  public Locale languageCode(String languageCode) {
    this.languageCode = languageCode;
    return this;
  }

  /**
   * Get languageCode
   * 
   * @return languageCode
   **/
  @JsonProperty(JSON_PROPERTY_LANGUAGE_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getLanguageCode() {
    return languageCode;
  }


  @JsonProperty(JSON_PROPERTY_LANGUAGE_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLanguageCode(String languageCode) {
    this.languageCode = languageCode;
  }


  public Locale regionCode(String regionCode) {
    this.regionCode = regionCode;
    return this;
  }

  /**
   * Get regionCode
   * 
   * @return regionCode
   **/
  @JsonProperty(JSON_PROPERTY_REGION_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getRegionCode() {
    return regionCode;
  }


  @JsonProperty(JSON_PROPERTY_REGION_CODE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setRegionCode(String regionCode) {
    this.regionCode = regionCode;
  }


  /**
   * Return true if this Locale object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Locale locale = (Locale) o;
    return Objects.equals(this.languageCode, locale.languageCode)
        && Objects.equals(this.regionCode, locale.regionCode);
  }

  @Override
  public int hashCode() {
    return Objects.hash(languageCode, regionCode);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Locale {\n");
    sb.append("    languageCode: ").append(toIndentedString(languageCode)).append("\n");
    sb.append("    regionCode: ").append(toIndentedString(regionCode)).append("\n");
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

