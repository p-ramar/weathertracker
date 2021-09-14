package com.zip.weather.tracker.repository;

import com.zip.weather.tracker.entity.CityConfig;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityConfigRepository extends CrudRepository<CityConfig, Long> {

  List<CityConfig> findAll();

  Optional<CityConfig> findByCityName(String cityName);
}
