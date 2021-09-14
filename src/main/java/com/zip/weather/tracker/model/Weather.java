package com.zip.weather.tracker.model;

import lombok.*;

import java.math.BigDecimal;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Weather {
  private Long id;
  private String name;
  private String weatherMain;
  private WeatherMain main;
  private BigDecimal windSpeed;
}
