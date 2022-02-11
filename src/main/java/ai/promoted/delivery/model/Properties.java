package ai.promoted.delivery.model;

import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Properties
 */
@JsonPropertyOrder({Properties.JSON_PROPERTY_STRUCT_FIELD})
public class Properties {
  public static final String JSON_PROPERTY_STRUCT_FIELD = "struct";
  private Map<String, Object> structField;

  public Properties() {}

  public Properties structField(Map<String, Object> structField) {
    this.structField = structField;
    return this;
  }

  /**
   * Get structField
   * 
   * @return structField
   **/
  @JsonProperty(JSON_PROPERTY_STRUCT_FIELD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getStructField() {
    return structField;
  }


  @JsonProperty(JSON_PROPERTY_STRUCT_FIELD)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setStructField(Map<String, Object> structField) {
    this.structField = structField;
  }


  /**
   * Return true if this Properties object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Properties properties = (Properties) o;
    return Objects.equals(this.structField, properties.structField);
  }

  @Override
  public int hashCode() {
    return Objects.hash(structField);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Properties {\n");
    sb.append("    structField: ").append(toIndentedString(structField)).append("\n");
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

