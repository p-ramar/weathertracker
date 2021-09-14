package com.zip.weather.tracker.repository;

import com.zip.weather.tracker.entity.CityWeather;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CityWeatherRepository extends CrudRepository<CityWeather, Long> {

  Optional<CityWeather> findByCityId(Long cityId);

  Optional<CityWeather> findByCityName(String cityName);

  List<CityWeather> findAll();
}
