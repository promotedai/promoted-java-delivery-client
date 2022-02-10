package ai.promoted.delivery.model;

import java.util.Map;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;


/**
 * Struct
 */
public class Struct {
  private Map<String, Object> fields;

  public Struct() {}

  public Struct fields(Map<String, Object> fields) {
    this.fields = fields;
    return this;
  }

  /**
   * Get fields
   * 
   * @return fields
   **/
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Map<String, Object> getFields() {
    return fields;
  }


  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setFields(Map<String, Object> fields) {
    this.fields = fields;
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
    Struct properties = (Struct) o;
    return Objects.equals(this.fields, properties.fields);
  }

  @Override
  public int hashCode() {
    return Objects.hash(fields);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Struct {\n");
    sb.append("    fields: ").append(toIndentedString(fields)).append("\n");
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

