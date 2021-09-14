package com.zip.weather.tracker.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@ToString
public class WeatherProfileResponse {
  private Long id;

  private Long userId;

  private String profileName;

  private List<Weather> weather;

  public List<Weather> getWeather() {
    if (weather == null) weather = new ArrayList<Weather>();
    return weather;
  }
}
