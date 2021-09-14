package com.zip.weather.tracker.service;

import com.zip.weather.tracker.entity.CityWeather;
import com.zip.weather.tracker.exception.ApplicationException;
import com.zip.weather.tracker.feed.CurrentWeatherData;
import com.zip.weather.tracker.model.Weather;
import com.zip.weather.tracker.model.WeatherMain;
import com.zip.weather.tracker.model.WeatherResponse;
import com.zip.weather.tracker.repository.CityWeatherRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import static com.zip.weather.tracker.exception.ErrorCodes.CITY_NOT_FOUND;

@Service
public class WeatherReportService {

  @Autowired CurrentWeatherData weatherData;

  @Autowired private CityWeatherRepository repository;

  /**
   * Gets the list of supported cities & their weather reports
   * @return the weather report for all supported cities.
   */
  public WeatherResponse retrieveWeatherReport() {
    WeatherResponse weatherResponse = new WeatherResponse();
    List<CityWeather> cityWeatherList = repository.findAll();
    weatherResponse
        .getWeather()
        .addAll(
            cityWeatherList
                .stream()
                .map(cityWeather -> createWeather(cityWeather))
                .collect(Collectors.toList()));
    return weatherResponse;
  }

  /**
   * Gets the current weather data for the given city.
   * @param city the city name for which the current weather data should be retrieved
   * @return the current weather data for the given city.
   */
  public WeatherResponse retrieveWeatherReportByCity(String city) {
    WeatherResponse weatherResponse = new WeatherResponse();
    CityWeather cityWeather =
        repository.findByCityName(city).orElseThrow(() -> new ApplicationException(CITY_NOT_FOUND));
    weatherResponse.getWeather().add(createWeather(cityWeather));
    return weatherResponse;
  }

  private Weather createWeather(CityWeather cityWeather) {
    Weather.WeatherBuilder weatherBuilder = Weather.builder();
    WeatherMain weatherMain =
        WeatherMain.builder()
            .temp(cityWeather.getTemp())
            .feelsLike(cityWeather.getFeelsLike())
            .tempMin(cityWeather.getTempMin())
            .tempMax(cityWeather.getTempMax())
            .pressure(cityWeather.getPressure())
            .humidity(cityWeather.getHumidity())
            .build();
    return weatherBuilder
        .name(cityWeather.getCityName())
        .id(cityWeather.getCityId())
        .weatherMain(cityWeather.getWeatherMain())
        .windSpeed(cityWeather.getWindSpeed())
        .main(weatherMain)
        .build();
  }
}
