package com.zip.weather.tracker.model;

import lombok.*;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
public class User {
  private Long id;

  @NotNull(message = "Name can't be null")
  private String name;

  @NotNull(message = "Email can't be null")
  private String email;
}
