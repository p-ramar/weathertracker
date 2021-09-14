package com.zip.weather.tracker.model;

import lombok.*;

import java.math.BigDecimal;
import java.math.BigInteger;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class WeatherMain {
  private BigDecimal temp;
  private BigDecimal feelsLike;
  private BigDecimal tempMin;
  private BigDecimal tempMax;
  private BigInteger pressure;
  private BigInteger humidity;
}
