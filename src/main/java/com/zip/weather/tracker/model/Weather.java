package com.zip.weather.tracker.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Weather {
  private Long id;
  private String name;
  private String weatherMain;
  private WeatherMain main;
  private BigDecimal windSpeed;
}
