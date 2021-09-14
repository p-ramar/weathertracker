package com.zip.weather.tracker.repository;

import com.zip.weather.tracker.entity.WeatherProfile;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface WeatherProfileRepository extends CrudRepository<WeatherProfile, Long> {

  Optional<WeatherProfile> findById(Long id);

  List<WeatherProfile> findByUserId(Long userId);

  Optional<WeatherProfile> findByIdAndUserId(Long id, Long userId);
}
