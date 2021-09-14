package com.zip.weather.tracker.model;

import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Setter
@Getter
@ToString
public class WeatherResponse {
  private List<Weather> weather;

  public List<Weather> getWeather() {
    if (weather == null) weather = new ArrayList<Weather>();
    return weather;
  }
}
