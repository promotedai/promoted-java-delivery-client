package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Screen
 */
@JsonPropertyOrder({Screen.JSON_PROPERTY_SCALE, Screen.JSON_PROPERTY_SIZE})
public class Screen {
  public static final String JSON_PROPERTY_SCALE = "scale";
  private Float scale;

  public static final String JSON_PROPERTY_SIZE = "size";
  private Size size;

  public Screen() {}

  public Screen scale(Float scale) {
    this.scale = scale;
    return this;
  }

  /**
   * Get scale
   * 
   * @return scale
   **/
  @JsonProperty(JSON_PROPERTY_SCALE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Float getScale() {
    return scale;
  }


  @JsonProperty(JSON_PROPERTY_SCALE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setScale(Float scale) {
    this.scale = scale;
  }


  public Screen size(Size size) {
    this.size = size;
    return this;
  }

  /**
   * Get size
   * 
   * @return size
   **/
  @JsonProperty(JSON_PROPERTY_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Size getSize() {
    return size;
  }


  @JsonProperty(JSON_PROPERTY_SIZE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setSize(Size size) {
    this.size = size;
  }


  /**
   * Return true if this Screen object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Screen screen = (Screen) o;
    return Objects.equals(this.scale, screen.scale) && Objects.equals(this.size, screen.size);
  }

  @Override
  public int hashCode() {
    return Objects.hash(scale, size);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Screen {\n");
    sb.append("    scale: ").append(toIndentedString(scale)).append("\n");
    sb.append("    size: ").append(toIndentedString(size)).append("\n");
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

