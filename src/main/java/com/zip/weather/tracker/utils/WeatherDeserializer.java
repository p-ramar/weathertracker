package com.zip.weather.tracker.utils;

import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.deser.std.StdDeserializer;
import com.zip.weather.tracker.model.Weather;
import com.zip.weather.tracker.model.WeatherMain;
import com.zip.weather.tracker.model.WeatherResponse;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

public class WeatherDeserializer extends StdDeserializer<WeatherResponse> {

  public WeatherDeserializer() {
    this(null);
  }

  public WeatherDeserializer(Class<?> vc) {
    super(vc);
  }

  @Override
  public WeatherResponse deserialize(JsonParser jp, DeserializationContext ctxt)
      throws IOException {
    WeatherResponse weatherResponse = new WeatherResponse();
    JsonNode node = jp.getCodec().readTree(jp);
    Iterator<JsonNode> nodeIterator = node.get("list").elements();
    List<Weather> weatherList =
        StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(nodeIterator, Spliterator.ORDERED), false)
            .map(listnode -> createWeather(listnode))
            .collect(Collectors.toList());
    weatherResponse.getWeather().addAll(weatherList);
    return weatherResponse;
  }

  private Weather createWeather(JsonNode listnode) {
    Weather.WeatherBuilder builder = Weather.builder();
    String weatherMain =
        StreamSupport.stream(
                Spliterators.spliteratorUnknownSize(
                    listnode.get("weather").elements(), Spliterator.ORDERED),
                false)
            .findFirst()
            .map(mainnode -> mainnode.get("main").asText())
            .orElse(null);
    builder
        .weatherMain(weatherMain)
        .name(listnode.get("name").asText())
        .id(listnode.get("id").asLong())
        .windSpeed(listnode.at("/wind/speed").decimalValue());
    WeatherMain.WeatherMainBuilder weatherMainBuilder = WeatherMain.builder();
    weatherMainBuilder
        .temp(listnode.at("/main/temp").decimalValue())
        .tempMin(listnode.at("/main/temp_min").decimalValue())
        .tempMax(listnode.at("/main/temp_max").decimalValue())
        .feelsLike(listnode.at("/main/feels_like").decimalValue())
        .pressure(listnode.at("/main/pressure").bigIntegerValue())
        .humidity(listnode.at("/main/humidity").bigIntegerValue());
    builder.main(weatherMainBuilder.build());
    return builder.build();
  }
}
