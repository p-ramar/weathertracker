package com.zip.weather.tracker.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "weather_profile")
public class WeatherProfile {

  @Id
  @Column(name = "id")
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Column(name = "user_id")
  private Long userId;

  @Column(name = "profile_name")
  private String profileName;

  @OneToMany(
    mappedBy = "weatherProfile",
    cascade = {CascadeType.ALL},
    fetch = FetchType.EAGER,
    orphanRemoval = true
  )
  private List<ProfileCityMap> profileCityMaps;

  @OneToMany
  @JoinTable(
    name = "profile_city_map",
    joinColumns = @JoinColumn(name = "profile_id"),
    inverseJoinColumns = @JoinColumn(name = "city_id")
  )
  private List<CityWeather> cityWeatherList;

  @Column(name = "created_timestamp")
  @CreatedDate
  private LocalDateTime createdOn;

  @Column(name = "updated_timestamp")
  @LastModifiedDate
  private LocalDateTime updatedOn;

  public List<ProfileCityMap> getProfileCityMaps() {
    if (profileCityMaps == null) profileCityMaps = new ArrayList<>();
    return profileCityMaps;
  }
}
