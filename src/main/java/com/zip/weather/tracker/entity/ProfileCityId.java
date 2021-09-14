package com.zip.weather.tracker.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

@Embeddable
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProfileCityId implements Serializable {
  private static final long serialVersionUID = 1L;

  @Column(name = "profile_id")
  private Long profileId;

  @Column(name = "city_id")
  private Long cityId;
}
