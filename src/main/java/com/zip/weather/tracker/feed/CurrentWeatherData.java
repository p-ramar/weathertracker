package com.zip.weather.tracker.feed;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.zip.weather.tracker.entity.CityConfig;
import com.zip.weather.tracker.exception.ApplicationException;
import com.zip.weather.tracker.model.WeatherResponse;
import com.zip.weather.tracker.utils.WeatherDeserializer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.stream.Collectors;

import static com.zip.weather.tracker.exception.ErrorCodes.SYSTEM_EXCEPTION;

@Component
@Slf4j
public class CurrentWeatherData {

  @Value("${weather.api.url}")
  private String apiUrl;

  @Value("${weather.api.key}")
  private String apiKey;

  /**
   * Retrieves the current weather data from weather API for all the configured cities.
   * @param cityConfigList the configured city list.
   * @return the current weather data for the given cities.
   */
  public WeatherResponse getCurrentWeather(List<CityConfig> cityConfigList) {

    HttpClient client = HttpClient.newHttpClient();
    WeatherResponse weatherResponse = null;
    try {
      String cityCommaSeparated =
          cityConfigList
              .stream()
              .map(cityConfig -> cityConfig.getCityId().toString())
              .collect(Collectors.joining(","));
      HttpRequest request =
          HttpRequest.newBuilder(
                  URI.create(
                      apiUrl + "?id=" + cityCommaSeparated + "&units=metric&appid=" + apiKey))
              .header("accept", "application/json")
              .build();

      HttpResponse response = client.send(request, HttpResponse.BodyHandlers.ofString());

      ObjectMapper mapper = new ObjectMapper();
      SimpleModule module = new SimpleModule();
      module.addDeserializer(WeatherResponse.class, new WeatherDeserializer());
      mapper.registerModule(module);
      weatherResponse = mapper.readValue(response.body().toString(), WeatherResponse.class);
    } catch (IOException | InterruptedException exception) {
      log.error("Exception while retriving weather data :", exception);
      throw new ApplicationException(SYSTEM_EXCEPTION);
    }
    return weatherResponse;
  }
}
