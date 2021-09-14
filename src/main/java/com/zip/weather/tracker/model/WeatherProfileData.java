package com.zip.weather.tracker.model;

import lombok.*;

import javax.validation.constraints.*;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
@Setter
public class WeatherProfileData {

  private Long id;

  @NotNull(message = "UserId can't be null")
  private Long userId;

  @NotNull(message = "Profile name can't be null")
  private String profileName;

  @Size(min = 1, message = "Cities can't be empty")
  private List<String> cities;
}
