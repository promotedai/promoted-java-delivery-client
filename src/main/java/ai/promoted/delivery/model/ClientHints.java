package ai.promoted.delivery.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * ClientHints
 */
@JsonPropertyOrder({ClientHints.JSON_PROPERTY_ARCHITECTURE, ClientHints.JSON_PROPERTY_BRAND,
    ClientHints.JSON_PROPERTY_IS_MOBILE, ClientHints.JSON_PROPERTY_MODEL,
    ClientHints.JSON_PROPERTY_PLATFORM, ClientHints.JSON_PROPERTY_PLATFORM_VERSION,
    ClientHints.JSON_PROPERTY_UA_FULL_VERSION})
public class ClientHints {
  public static final String JSON_PROPERTY_ARCHITECTURE = "architecture";
  private String architecture;

  public static final String JSON_PROPERTY_BRAND = "brand";
  private List<ClientHintBrand> brand = null;

  public static final String JSON_PROPERTY_IS_MOBILE = "isMobile";
  private Boolean isMobile;

  public static final String JSON_PROPERTY_MODEL = "model";
  private String model;

  public static final String JSON_PROPERTY_PLATFORM = "platform";
  private String platform;

  public static final String JSON_PROPERTY_PLATFORM_VERSION = "platformVersion";
  private String platformVersion;

  public static final String JSON_PROPERTY_UA_FULL_VERSION = "uaFullVersion";
  private String uaFullVersion;

  public ClientHints() {}

  public ClientHints architecture(String architecture) {
    this.architecture = architecture;
    return this;
  }

  /**
   * Get architecture
   * 
   * @return architecture
   **/
  @JsonProperty(JSON_PROPERTY_ARCHITECTURE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getArchitecture() {
    return architecture;
  }


  @JsonProperty(JSON_PROPERTY_ARCHITECTURE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setArchitecture(String architecture) {
    this.architecture = architecture;
  }


  public ClientHints brand(List<ClientHintBrand> brand) {
    this.brand = brand;
    return this;
  }

  public ClientHints addBrandItem(ClientHintBrand brandItem) {
    if (this.brand == null) {
      this.brand = new ArrayList<>();
    }
    this.brand.add(brandItem);
    return this;
  }

  /**
   * Get brand
   * 
   * @return brand
   **/
  @JsonProperty(JSON_PROPERTY_BRAND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public List<ClientHintBrand> getBrand() {
    return brand;
  }


  @JsonProperty(JSON_PROPERTY_BRAND)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBrand(List<ClientHintBrand> brand) {
    this.brand = brand;
  }


  public ClientHints isMobile(Boolean isMobile) {
    this.isMobile = isMobile;
    return this;
  }

  /**
   * Get isMobile
   * 
   * @return isMobile
   **/
  @JsonProperty(JSON_PROPERTY_IS_MOBILE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Boolean getIsMobile() {
    return isMobile;
  }


  @JsonProperty(JSON_PROPERTY_IS_MOBILE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIsMobile(Boolean isMobile) {
    this.isMobile = isMobile;
  }


  public ClientHints model(String model) {
    this.model = model;
    return this;
  }

  /**
   * Get model
   * 
   * @return model
   **/
  @JsonProperty(JSON_PROPERTY_MODEL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getModel() {
    return model;
  }


  @JsonProperty(JSON_PROPERTY_MODEL)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setModel(String model) {
    this.model = model;
  }


  public ClientHints platform(String platform) {
    this.platform = platform;
    return this;
  }

  /**
   * Get platform
   * 
   * @return platform
   **/
  @JsonProperty(JSON_PROPERTY_PLATFORM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPlatform() {
    return platform;
  }


  @JsonProperty(JSON_PROPERTY_PLATFORM)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPlatform(String platform) {
    this.platform = platform;
  }


  public ClientHints platformVersion(String platformVersion) {
    this.platformVersion = platformVersion;
    return this;
  }

  /**
   * Get platformVersion
   * 
   * @return platformVersion
   **/
  @JsonProperty(JSON_PROPERTY_PLATFORM_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPlatformVersion() {
    return platformVersion;
  }


  @JsonProperty(JSON_PROPERTY_PLATFORM_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPlatformVersion(String platformVersion) {
    this.platformVersion = platformVersion;
  }


  public ClientHints uaFullVersion(String uaFullVersion) {
    this.uaFullVersion = uaFullVersion;
    return this;
  }

  /**
   * Get uaFullVersion
   * 
   * @return uaFullVersion
   **/
  @JsonProperty(JSON_PROPERTY_UA_FULL_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getUaFullVersion() {
    return uaFullVersion;
  }


  @JsonProperty(JSON_PROPERTY_UA_FULL_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setUaFullVersion(String uaFullVersion) {
    this.uaFullVersion = uaFullVersion;
  }


  /**
   * Return true if this ClientHints object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ClientHints clientHints = (ClientHints) o;
    return Objects.equals(this.architecture, clientHints.architecture)
        && Objects.equals(this.brand, clientHints.brand)
        && Objects.equals(this.isMobile, clientHints.isMobile)
        && Objects.equals(this.model, clientHints.model)
        && Objects.equals(this.platform, clientHints.platform)
        && Objects.equals(this.platformVersion, clientHints.platformVersion)
        && Objects.equals(this.uaFullVersion, clientHints.uaFullVersion);
  }

  @Override
  public int hashCode() {
    return Objects.hash(architecture, brand, isMobile, model, platform, platformVersion,
        uaFullVersion);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class ClientHints {\n");
    sb.append("    architecture: ").append(toIndentedString(architecture)).append("\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    isMobile: ").append(toIndentedString(isMobile)).append("\n");
    sb.append("    model: ").append(toIndentedString(model)).append("\n");
    sb.append("    platform: ").append(toIndentedString(platform)).append("\n");
    sb.append("    platformVersion: ").append(toIndentedString(platformVersion)).append("\n");
    sb.append("    uaFullVersion: ").append(toIndentedString(uaFullVersion)).append("\n");
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

