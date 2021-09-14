package com.zip.weather.tracker.controller;

import com.zip.weather.tracker.model.WeatherResponse;
import com.zip.weather.tracker.service.WeatherReportService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Validated
@Slf4j
@RequestMapping("/report")
public class WeatherReportController {

  @Autowired WeatherReportService weatherReportService;

  /**
   * Gets the list of supported cities & their weather reports
   * @return the weather report for all supported cities.
   */
  @GetMapping("/all")
  public ResponseEntity<WeatherResponse> getWeatherReport() {
    log.info("Retrieving complete weather report for all cities.");
    WeatherResponse weatherResponse = weatherReportService.retrieveWeatherReport();
    return new ResponseEntity<WeatherResponse>(weatherResponse, HttpStatus.OK);
  }

  /**
   * Gets the current weather data for the given city.
   * @param cityName the city name for which the current weather data should be retrieved
   * @return the current weather data for the given city.
   */
  @GetMapping("/city/{cityName}")
  public ResponseEntity<WeatherResponse> getWeatherReportByCity(
      @PathVariable(value = "cityName") final String cityName) {
    log.info("Retrieving weather report for city:{}",cityName);
    WeatherResponse weatherResponse = weatherReportService.retrieveWeatherReportByCity(cityName);
    return new ResponseEntity<WeatherResponse>(weatherResponse, HttpStatus.OK);
  }
}
