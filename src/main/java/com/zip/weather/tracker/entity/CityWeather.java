package com.zip.weather.tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDateTime;

@Entity
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EntityListeners(AuditingEntityListener.class)
@Table(name = "city_weather")
public class CityWeather {

  @Id
  @Column(name = "city_id")
  private Long cityId;

  @Column(name = "city_name")
  private String cityName;

  @Column(name = "weather_main")
  private String weatherMain;

  @Column(name = "wind_speed")
  private BigDecimal windSpeed;

  @Column(name = "temp")
  private BigDecimal temp;

  @Column(name = "feels_like")
  private BigDecimal feelsLike;

  @Column(name = "temp_min")
  private BigDecimal tempMin;

  @Column(name = "temp_max")
  private BigDecimal tempMax;

  @Column(name = "pressure")
  private BigInteger pressure;

  @Column(name = "humidity")
  private BigInteger humidity;

  @Column(name = "created_timestamp")
  @CreatedDate
  private LocalDateTime createdOn;

  @Column(name = "updated_timestamp")
  @LastModifiedDate
  private LocalDateTime updatedOn;
}
