package com.zip.weather.tracker.scheduler;

import com.zip.weather.tracker.service.LoadWeatherFeedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class WeatherDataScheduler {
  @Autowired LoadWeatherFeedService weatherFeedServiceService;

  @Scheduled(fixedRate = 15 * 60 * 1000)
  public void loadData() {
    log.info("Loading weather data....");
    weatherFeedServiceService.loadWeatherInitialData();
  }
}
