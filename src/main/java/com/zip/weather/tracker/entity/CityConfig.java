package com.zip.weather.tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "city_config")
public class CityConfig {

  @Id
  @Column(name = "city_id")
  private Long cityId;

  @Column(name = "city_name")
  private String cityName;

  @Column(name = "created_timestamp")
  @CreatedDate
  private LocalDateTime createdOn;

  @Column(name = "updated_timestamp")
  @LastModifiedDate
  private LocalDateTime updatedOn;
}
