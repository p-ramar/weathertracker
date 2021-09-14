package com.zip.weather.tracker.entity;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(
  name = "profile_city_map",
  uniqueConstraints = {@UniqueConstraint(columnNames = {"profile_id", "city_id"})}
)
public class ProfileCityMap {

  @EmbeddedId private ProfileCityId profileCityId;

  @Column(name = "created_timestamp")
  @CreatedDate
  private LocalDateTime createdOn;

  @Column(name = "updated_timestamp")
  @LastModifiedDate
  private LocalDateTime updatedOn;

  @ManyToOne(optional = true)
  @JoinColumn(name = "profile_id", referencedColumnName = "id")
  @MapsId("profileId")
  private WeatherProfile weatherProfile;
}
