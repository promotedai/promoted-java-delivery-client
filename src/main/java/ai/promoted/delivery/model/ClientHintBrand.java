package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ClientHintBrand
 */
@JsonPropertyOrder({ClientHintBrand.JSON_PROPERTY_BRAND, ClientHintBrand.JSON_PROPERTY_VERSION})
public class ClientHintBrand {
  public static final String JSON_PROPERTY_BRAND = "brand";
  private String brand;

  public static final String JSON_PROPERTY_VERSION = "version";
  private String version;

  public ClientHintBrand() {}

  public ClientHintBrand brand(String brand) {
    this.brand = brand;
    return this;
  }

  /**
   * Get brand
   * 
   * @return brand
   **/
  @JsonProperty(JSON_PROPERTY_BRAND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getBrand() {
    return brand;
  }


  @JsonProperty(JSON_PROPERTY_BRAND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBrand(String brand) {
    this.brand = brand;
  }


  public ClientHintBrand version(String version) {
    this.version = version;
    return this;
  }

  /**
   * Get version
   * 
   * @return version
   **/
  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getVersion() {
    return version;
  }


  @JsonProperty(JSON_PROPERTY_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setVersion(String version) {
    this.version = version;
  }


  /**
   * Return true if this ClientHintBrand object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientHintBrand clientHintBrand = (ClientHintBrand) o;
    return Objects.equals(this.brand, clientHintBrand.brand)
        && Objects.equals(this.version, clientHintBrand.version);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, version);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientHintBrand {\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    version: ").append(toIndentedString(version)).append("\n");
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

