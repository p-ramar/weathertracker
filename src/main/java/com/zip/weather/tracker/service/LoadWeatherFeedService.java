package com.zip.weather.tracker.service;

import com.zip.weather.tracker.entity.CityWeather;
import com.zip.weather.tracker.feed.CurrentWeatherData;
import com.zip.weather.tracker.model.Weather;
import com.zip.weather.tracker.model.WeatherResponse;
import com.zip.weather.tracker.repository.CityConfigRepository;
import com.zip.weather.tracker.repository.CityWeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j 
public class LoadWeatherFeedService {

  @Autowired CurrentWeatherData weatherData;

  @Autowired private CityWeatherRepository cityWeatherRepository;

  @Autowired private CityConfigRepository cityConfigRepository;

  /**
   * Loads current weather data from open Weather API
   * @return the current weather data for all the configured cities.
   */
  public WeatherResponse loadWeatherInitialData() {

    WeatherResponse response = weatherData.getCurrentWeather(cityConfigRepository.findAll());
    List<CityWeather> cityWeatherList =
        response
            .getWeather()
            .stream()
            .map(weather -> createCityWeather(weather))
            .collect(Collectors.toList());
    cityWeatherRepository.saveAll(cityWeatherList);
    log.info("Successfully saved weather data:{}",response);
    return response;
  }

  private CityWeather createCityWeather(Weather weather) {
    CityWeather.CityWeatherBuilder cityWeatherBuilder = CityWeather.builder();
    return cityWeatherBuilder
        .cityName(weather.getName())
        .cityId(weather.getId())
        .weatherMain(weather.getWeatherMain())
        .windSpeed(weather.getWindSpeed())
        .temp(weather.getMain().getTemp())
        .tempMin(weather.getMain().getTempMin())
        .tempMax(weather.getMain().getTempMax())
        .feelsLike(weather.getMain().getFeelsLike())
        .pressure(weather.getMain().getPressure())
        .humidity(weather.getMain().getHumidity())
        .build();
  }
}
