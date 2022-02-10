package ai.promoted.delivery.model;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;


/**
 * Location
 */
@JsonPropertyOrder({Location.JSON_PROPERTY_ACCURACY_IN_METERS, Location.JSON_PROPERTY_LATITUDE,
    Location.JSON_PROPERTY_LONGITUDE})
public class Location {
  public static final String JSON_PROPERTY_ACCURACY_IN_METERS = "accuracyInMeters";
  private Double accuracyInMeters;

  public static final String JSON_PROPERTY_LATITUDE = "latitude";
  private Double latitude;

  public static final String JSON_PROPERTY_LONGITUDE = "longitude";
  private Double longitude;

  public Location() {}

  public Location accuracyInMeters(Double accuracyInMeters) {
    this.accuracyInMeters = accuracyInMeters;
    return this;
  }

  /**
   * Get accuracyInMeters
   * 
   * @return accuracyInMeters
   **/
  @JsonProperty(JSON_PROPERTY_ACCURACY_IN_METERS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Double getAccuracyInMeters() {
    return accuracyInMeters;
  }


  @JsonProperty(JSON_PROPERTY_ACCURACY_IN_METERS)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setAccuracyInMeters(Double accuracyInMeters) {
    this.accuracyInMeters = accuracyInMeters;
  }


  public Location latitude(Double latitude) {
    this.latitude = latitude;
    return this;
  }

  /**
   * Get latitude
   * 
   * @return latitude
   **/
  @JsonProperty(JSON_PROPERTY_LATITUDE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Double getLatitude() {
    return latitude;
  }


  @JsonProperty(JSON_PROPERTY_LATITUDE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLatitude(Double latitude) {
    this.latitude = latitude;
  }


  public Location longitude(Double longitude) {
    this.longitude = longitude;
    return this;
  }

  /**
   * Get longitude
   * 
   * @return longitude
   **/
  @JsonProperty(JSON_PROPERTY_LONGITUDE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)

  public Double getLongitude() {
    return longitude;
  }


  @JsonProperty(JSON_PROPERTY_LONGITUDE)
  @JsonInclude(value = JsonInclude.Include.USE_DEFAULTS)
  public void setLongitude(Double longitude) {
    this.longitude = longitude;
  }


  /**
   * Return true if this Location object is equal to o.
   */
  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    Location location = (Location) o;
    return Objects.equals(this.accuracyInMeters, location.accuracyInMeters)
        && Objects.equals(this.latitude, location.latitude)
        && Objects.equals(this.longitude, location.longitude);
  }

  @Override
  public int hashCode() {
    return Objects.hash(accuracyInMeters, latitude, longitude);
  }

  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class Location {\n");
    sb.append("    accuracyInMeters: ").append(toIndentedString(accuracyInMeters)).append("\n");
    sb.append("    latitude: ").append(toIndentedString(latitude)).append("\n");
    sb.append("    longitude: ").append(toIndentedString(longitude)).append("\n");
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

