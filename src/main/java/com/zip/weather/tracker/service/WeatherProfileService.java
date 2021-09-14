package com.zip.weather.tracker.service;

import com.zip.weather.tracker.entity.*;
import com.zip.weather.tracker.exception.ApplicationException;
import com.zip.weather.tracker.feed.CurrentWeatherData;
import com.zip.weather.tracker.model.Weather;
import com.zip.weather.tracker.model.WeatherMain;
import com.zip.weather.tracker.model.WeatherProfileData;
import com.zip.weather.tracker.model.WeatherProfileResponse;
import com.zip.weather.tracker.repository.CityConfigRepository;
import com.zip.weather.tracker.repository.ProfileCityMapRepository;
import com.zip.weather.tracker.repository.UserRepository;
import com.zip.weather.tracker.repository.WeatherProfileRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.zip.weather.tracker.exception.ErrorCodes.*;

@Service
@Slf4j
public class WeatherProfileService {

  @Autowired CurrentWeatherData weatherData;

  @Autowired CityConfigRepository cityConfigRepository;
  @Autowired UserRepository userRepository;
  @Autowired private WeatherProfileRepository weatherProfileRepository;
  @Autowired private ProfileCityMapRepository profileCityMapRepository;

  /**
   * Creates a new weather profile.
   * @param weatherProfileData the weather profile details to create new profile.
   * @return the updated WeatherProfileData details with the id value
   */
  public WeatherProfileData createProfile(WeatherProfileData weatherProfileData) {
    WeatherProfile weatherProfile =
        WeatherProfile.builder()
            .profileName(weatherProfileData.getProfileName())
            .userId(weatherProfileData.getUserId())
            .build();
    List<ProfileCityMap> profileCityMapList = new ArrayList<>();
    for (String city : weatherProfileData.getCities()) {
      profileCityMapList.add(createProfileCityMap(city, weatherProfile));
    }
    weatherProfile.getProfileCityMaps().addAll(profileCityMapList);
    WeatherProfile updatedProfile = weatherProfileRepository.save(weatherProfile);
    weatherProfileData.setId(updatedProfile.getId());
    log.info("Successfully created weather profile:{}",weatherProfileData);
    return weatherProfileData;
  }

  private ProfileCityMap createProfileCityMap(String city, WeatherProfile weatherProfile) {
    CityConfig cityConfig =
        cityConfigRepository
            .findByCityName(city)
            .orElseThrow(() -> new ApplicationException(CITY_NOT_FOUND));
    if (weatherProfile
        .getProfileCityMaps()
        .stream()
        .filter(
            profileCityMap ->
                profileCityMap.getProfileCityId().getCityId().equals(cityConfig.getCityId()))
        .findAny()
        .isPresent()) throw new ApplicationException(CITY_AlREADY_ADDED);
    ProfileCityId profileCityId = ProfileCityId.builder().cityId(cityConfig.getCityId()).build();
    return ProfileCityMap.builder()
        .profileCityId(profileCityId)
        .weatherProfile(weatherProfile)
        .build();
  }

  /**
   * Updates weather profile name.
   * @param id the id of the weather profile
   * @param name the new name for the weather profile
   * @return the updated Weather profile data after removing the city
   */
  public WeatherProfileData updateProfileName(String id, String name) {
    WeatherProfile weatherProfile =
        weatherProfileRepository
            .findById(Long.valueOf(id))
            .orElseThrow(() -> new ApplicationException(PROFILE_NOT_FOUND));
    weatherProfile.setProfileName(name);
    WeatherProfile updatedProfile = weatherProfileRepository.save(weatherProfile);
    WeatherProfileData  weatherProfileData =createWeatherProfileData(updatedProfile);
    log.info("Successfully updated weather profile:{}",weatherProfileData);
    return weatherProfileData;
  }

  /**
   * Adds new city to the weather profile.
   * @param id the id of the weather profile to which city should be added
   * @param cityName the name of the city that should be added to profile
   * @return the updated weather profile with the new city added in it.
   */
  public WeatherProfileData addCity(String id, String cityName) {
    WeatherProfile weatherProfile =
        weatherProfileRepository
            .findById(Long.valueOf(id))
            .orElseThrow(() -> new ApplicationException(PROFILE_NOT_FOUND));
    weatherProfile.getProfileCityMaps().add(createProfileCityMap(cityName, weatherProfile));
    WeatherProfile updatedProfile = weatherProfileRepository.save(weatherProfile);
    WeatherProfileData  weatherProfileData =createWeatherProfileData(updatedProfile);
    log.info("Successfully added city to weather profile:{}",weatherProfileData);
    return weatherProfileData;
  }

  /**
   * Deletes a city in the given weather profile
   * @param id the id of the weather profile from which the city should be deleted
   * @param cityName - the name of the city which should be deleted.
   * @return the updated Weather profile data after removing the city
   */
  public WeatherProfileData deleteCity(String id, String cityName) {
    WeatherProfile weatherProfile =
        weatherProfileRepository
            .findById(Long.valueOf(id))
            .orElseThrow(() -> new ApplicationException(PROFILE_NOT_FOUND));
    CityConfig cityConfig =
        cityConfigRepository
            .findByCityName(cityName)
            .orElseThrow(() -> new ApplicationException(CITY_NOT_FOUND));
    ProfileCityMap profileCityMap =
        weatherProfile
            .getProfileCityMaps()
            .stream()
            .filter(
                profileCity ->
                    Objects.equals(
                        profileCity.getProfileCityId().getCityId(), cityConfig.getCityId()))
            .findFirst()
            .get();
    weatherProfile.getProfileCityMaps().remove(profileCityMap);
    WeatherProfile updatedProfile = weatherProfileRepository.save(weatherProfile);
    WeatherProfileData  weatherProfileData =createWeatherProfileData(updatedProfile);
    log.info("Successfully deleted city from weather profile:{}",weatherProfileData);
    return weatherProfileData;
  }

  private WeatherProfileData createWeatherProfileData(WeatherProfile updatedProfile) {
    List<CityConfig> cityConfigList = cityConfigRepository.findAll();
    List<Long> cityIdList =
        updatedProfile
            .getProfileCityMaps()
            .stream()
            .map(profileCity -> profileCity.getProfileCityId().getCityId())
            .collect(Collectors.toList());
    List<String> cities =
        cityConfigList
            .stream()
            .filter(cityConfig -> cityIdList.contains(cityConfig.getCityId()))
            .map(cityConfig -> cityConfig.getCityName())
            .collect(Collectors.toList());
    return WeatherProfileData.builder()
        .id(updatedProfile.getId())
        .profileName(updatedProfile.getProfileName())
        .userId(updatedProfile.getUserId())
        .cities(cities)
        .build();
  }

  /**
   * Deletes the given weather profile
   * @param id - the id of the profile which should be deleted.
   */
  public void deleteProfile(String id) {
    WeatherProfile weatherProfile =
        weatherProfileRepository
            .findById(Long.valueOf(id))
            .orElseThrow(() -> new ApplicationException(PROFILE_NOT_FOUND));
    weatherProfile.getProfileCityMaps().clear();
    weatherProfileRepository.save(weatherProfile);
    weatherProfileRepository.delete(weatherProfile);
    log.info("Successfully deleted weather profile with id:{}",id);
  }

  /**
   * Gets all the weather profiles with the current city weather data for a given user id.
   * @param userId the user id for which weather data should be retrieved.
   * @return the complete weather profile with current weather data for all the configured cities
   */
  public List<WeatherProfileResponse> getUserProfiles(String userId) {
    userRepository
        .findById(Long.valueOf(userId))
        .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND));
    List<WeatherProfile> weatherProfileList =
        weatherProfileRepository.findByUserId(Long.valueOf(userId));
    if (weatherProfileList.size() == 0) throw new ApplicationException(PROFILE_NOT_FOUND);
    List<WeatherProfileResponse> weatherProfileResponseList = weatherProfileList
        .stream()
        .map(weatherProfile -> createWeaterProfileResponse(weatherProfile))
        .collect(Collectors.toList());
    log.info("Successfully retrieved weather profile list:{}",weatherProfileResponseList);
    return weatherProfileResponseList;
  }

  /**
   * Gets the current city weather data for a given user id and profile id.
   *
   * @param userId the user id for which weather data should be retrieved.
   * @param profileId the profile id for which weather data should be retrieved.
   * @return the complete weather profile with current weather data for the given user id and profile id.
   */
  public WeatherProfileResponse getUserProfile(String userId, String profileId) {
    userRepository
            .findById(Long.valueOf(userId))
            .orElseThrow(() -> new ApplicationException(USER_NOT_FOUND));
    WeatherProfile weatherProfile =
            weatherProfileRepository.findByIdAndUserId(Long.valueOf(profileId),Long.valueOf(userId))
                    .orElseThrow(() -> new ApplicationException(PROFILE_NOT_FOUND));
     WeatherProfileResponse weatherProfileResponse = createWeaterProfileResponse(weatherProfile);
    log.info("Successfully retrieved weather profile:{}",weatherProfileResponse);
    return weatherProfileResponse;
  }

  private WeatherProfileResponse createWeaterProfileResponse(WeatherProfile weatherProfile) {
    WeatherProfileResponse.WeatherProfileResponseBuilder builder =
        WeatherProfileResponse.builder()
            .id(weatherProfile.getId())
            .profileName(weatherProfile.getProfileName())
            .userId(weatherProfile.getUserId());
    List<Weather> weatherList =
        weatherProfile
            .getCityWeatherList()
            .stream()
            .map(cityWeather -> createWeather(cityWeather))
            .collect(Collectors.toList());
    return builder.weather(weatherList).build();
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
