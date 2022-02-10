package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Device
 */
@JsonPropertyOrder({Device.JSON_PROPERTY_BRAND, Device.JSON_PROPERTY_BROWSER,
    Device.JSON_PROPERTY_DEVICE_TYPE, Device.JSON_PROPERTY_IDENTIFIER,
    Device.JSON_PROPERTY_IP_ADDRESS, Device.JSON_PROPERTY_LOCALE, Device.JSON_PROPERTY_LOCATION,
    Device.JSON_PROPERTY_MANUFACTURER, Device.JSON_PROPERTY_OS_VERSION,
    Device.JSON_PROPERTY_PLATFORM_APP_VERSION, Device.JSON_PROPERTY_PROMOTED_MOBILE_SDK_VERSION,
    Device.JSON_PROPERTY_SCREEN})
public class Device {
  public static final String JSON_PROPERTY_BRAND = "brand";
  private String brand;

  public static final String JSON_PROPERTY_BROWSER = "browser";
  private Browser browser;

  public static final String JSON_PROPERTY_DEVICE_TYPE = "deviceType";
  private Integer deviceType;

  public static final String JSON_PROPERTY_IDENTIFIER = "identifier";
  private String identifier;

  public static final String JSON_PROPERTY_IP_ADDRESS = "ipAddress";
  private String ipAddress;

  public static final String JSON_PROPERTY_LOCALE = "locale";
  private Locale locale;

  public static final String JSON_PROPERTY_LOCATION = "location";
  private Location location;

  public static final String JSON_PROPERTY_MANUFACTURER = "manufacturer";
  private String manufacturer;

  public static final String JSON_PROPERTY_OS_VERSION = "osVersion";
  private String osVersion;

  public static final String JSON_PROPERTY_PLATFORM_APP_VERSION = "platformAppVersion";
  private String platformAppVersion;

  public static final String JSON_PROPERTY_PROMOTED_MOBILE_SDK_VERSION = "promotedMobileSdkVersion";
  private String promotedMobileSdkVersion;

  public static final String JSON_PROPERTY_SCREEN = "screen";
  private Screen screen;

  public Device() {}

  public Device brand(String brand) {
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


  public Device browser(Browser browser) {
    this.browser = browser;
    return this;
  }

  /**
   * Get browser
   * 
   * @return browser
   **/
  @JsonProperty(JSON_PROPERTY_BROWSER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Browser getBrowser() {
    return browser;
  }


  @JsonProperty(JSON_PROPERTY_BROWSER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setBrowser(Browser browser) {
    this.browser = browser;
  }


  public Device deviceType(Integer deviceType) {
    this.deviceType = deviceType;
    return this;
  }

  /**
   * Get deviceType
   * 
   * @return deviceType
   **/
  @JsonProperty(JSON_PROPERTY_DEVICE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Integer getDeviceType() {
    return deviceType;
  }


  @JsonProperty(JSON_PROPERTY_DEVICE_TYPE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setDeviceType(Integer deviceType) {
    this.deviceType = deviceType;
  }


  public Device identifier(String identifier) {
    this.identifier = identifier;
    return this;
  }

  /**
   * Get identifier
   * 
   * @return identifier
   **/
  @JsonProperty(JSON_PROPERTY_IDENTIFIER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getIdentifier() {
    return identifier;
  }


  @JsonProperty(JSON_PROPERTY_IDENTIFIER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIdentifier(String identifier) {
    this.identifier = identifier;
  }


  public Device ipAddress(String ipAddress) {
    this.ipAddress = ipAddress;
    return this;
  }

  /**
   * Get ipAddress
   * 
   * @return ipAddress
   **/
  @JsonProperty(JSON_PROPERTY_IP_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getIpAddress() {
    return ipAddress;
  }


  @JsonProperty(JSON_PROPERTY_IP_ADDRESS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setIpAddress(String ipAddress) {
    this.ipAddress = ipAddress;
  }


  public Device locale(Locale locale) {
    this.locale = locale;
    return this;
  }

  /**
   * Get locale
   * 
   * @return locale
   **/
  @JsonProperty(JSON_PROPERTY_LOCALE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Locale getLocale() {
    return locale;
  }


  @JsonProperty(JSON_PROPERTY_LOCALE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocale(Locale locale) {
    this.locale = locale;
  }


  public Device location(Location location) {
    this.location = location;
    return this;
  }

  /**
   * Get location
   * 
   * @return location
   **/
  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Location getLocation() {
    return location;
  }


  @JsonProperty(JSON_PROPERTY_LOCATION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLocation(Location location) {
    this.location = location;
  }


  public Device manufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
    return this;
  }

  /**
   * Get manufacturer
   * 
   * @return manufacturer
   **/
  @JsonProperty(JSON_PROPERTY_MANUFACTURER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getManufacturer() {
    return manufacturer;
  }


  @JsonProperty(JSON_PROPERTY_MANUFACTURER)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setManufacturer(String manufacturer) {
    this.manufacturer = manufacturer;
  }


  public Device osVersion(String osVersion) {
    this.osVersion = osVersion;
    return this;
  }

  /**
   * Get osVersion
   * 
   * @return osVersion
   **/
  @JsonProperty(JSON_PROPERTY_OS_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getOsVersion() {
    return osVersion;
  }


  @JsonProperty(JSON_PROPERTY_OS_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setOsVersion(String osVersion) {
    this.osVersion = osVersion;
  }


  public Device platformAppVersion(String platformAppVersion) {
    this.platformAppVersion = platformAppVersion;
    return this;
  }

  /**
   * Get platformAppVersion
   * 
   * @return platformAppVersion
   **/
  @JsonProperty(JSON_PROPERTY_PLATFORM_APP_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPlatformAppVersion() {
    return platformAppVersion;
  }


  @JsonProperty(JSON_PROPERTY_PLATFORM_APP_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPlatformAppVersion(String platformAppVersion) {
    this.platformAppVersion = platformAppVersion;
  }


  public Device promotedMobileSdkVersion(String promotedMobileSdkVersion) {
    this.promotedMobileSdkVersion = promotedMobileSdkVersion;
    return this;
  }

  /**
   * Get promotedMobileSdkVersion
   * 
   * @return promotedMobileSdkVersion
   **/
  @JsonProperty(JSON_PROPERTY_PROMOTED_MOBILE_SDK_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public String getPromotedMobileSdkVersion() {
    return promotedMobileSdkVersion;
  }


  @JsonProperty(JSON_PROPERTY_PROMOTED_MOBILE_SDK_VERSION)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setPromotedMobileSdkVersion(String promotedMobileSdkVersion) {
    this.promotedMobileSdkVersion = promotedMobileSdkVersion;
  }


  public Device screen(Screen screen) {
    this.screen = screen;
    return this;
  }

  /**
   * Get screen
   * 
   * @return screen
   **/
  @JsonProperty(JSON_PROPERTY_SCREEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Screen getScreen() {
    return screen;
  }


  @JsonProperty(JSON_PROPERTY_SCREEN)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setScreen(Screen screen) {
    this.screen = screen;
  }


  /**
   * Return true if this Device object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Device device = (Device) o;
    return Objects.equals(this.brand, device.brand) && Objects.equals(this.browser, device.browser)
        && Objects.equals(this.deviceType, device.deviceType)
        && Objects.equals(this.identifier, device.identifier)
        && Objects.equals(this.ipAddress, device.ipAddress)
        && Objects.equals(this.locale, device.locale)
        && Objects.equals(this.location, device.location)
        && Objects.equals(this.manufacturer, device.manufacturer)
        && Objects.equals(this.osVersion, device.osVersion)
        && Objects.equals(this.platformAppVersion, device.platformAppVersion)
        && Objects.equals(this.promotedMobileSdkVersion, device.promotedMobileSdkVersion)
        && Objects.equals(this.screen, device.screen);
  }

  @Override
  public int hashCode() {
    return Objects.hash(brand, browser, deviceType, identifier, ipAddress, locale, location,
        manufacturer, osVersion, platformAppVersion, promotedMobileSdkVersion, screen);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Device {\n");
    sb.append("    brand: ").append(toIndentedString(brand)).append("\n");
    sb.append("    browser: ").append(toIndentedString(browser)).append("\n");
    sb.append("    deviceType: ").append(toIndentedString(deviceType)).append("\n");
    sb.append("    identifier: ").append(toIndentedString(identifier)).append("\n");
    sb.append("    ipAddress: ").append(toIndentedString(ipAddress)).append("\n");
    sb.append("    locale: ").append(toIndentedString(locale)).append("\n");
    sb.append("    location: ").append(toIndentedString(location)).append("\n");
    sb.append("    manufacturer: ").append(toIndentedString(manufacturer)).append("\n");
    sb.append("    osVersion: ").append(toIndentedString(osVersion)).append("\n");
    sb.append("    platformAppVersion: ").append(toIndentedString(platformAppVersion)).append("\n");
    sb.append("    promotedMobileSdkVersion: ").append(toIndentedString(promotedMobileSdkVersion))
        .append("\n");
    sb.append("    screen: ").append(toIndentedString(screen)).append("\n");
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

