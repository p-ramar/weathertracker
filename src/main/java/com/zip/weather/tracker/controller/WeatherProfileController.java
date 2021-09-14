package com.zip.weather.tracker.controller;

import com.zip.weather.tracker.model.WeatherProfileData;
import com.zip.weather.tracker.model.WeatherProfileResponse;
import com.zip.weather.tracker.service.WeatherProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.util.List;

@RestController
@Validated
@RequestMapping("/profile")
public class WeatherProfileController {
  @Autowired WeatherProfileService weatherProfileService;

  /**
   * Creates a new weather profile.
   *
   * @param weatherProfile the weather profile details to create new profile.
   * @return the updated WeatherProfile details with the id value
   */
  @PostMapping(path = "/create", produces = "application/json", consumes = "application/json")
  public ResponseEntity<WeatherProfileData> createProfile(
      @RequestBody @Valid WeatherProfileData weatherProfile) {
    WeatherProfileData weatherProfileDetails = weatherProfileService.createProfile(weatherProfile);
    return new ResponseEntity<WeatherProfileData>(weatherProfileDetails, HttpStatus.OK);
  }

  /**
   * Deletes the requested weather profile
   *
   * @param id - the id of the profile which should be deleted.
   * @return - NO CONTENT status
   */
  @DeleteMapping(path = "/delete/id/{id}")
  public ResponseEntity deleteProfile(
      @PathVariable(value = "id") @Pattern(regexp = "^[0-9]+$", message = "Invalid profile Id")
      final String id) {
    weatherProfileService.deleteProfile(id);
    return ResponseEntity.noContent().build();
  }

  /**
   * Adds new city to the weather profile.
   *
   * @param id       the id of the weather profile to which city should be added
   * @param cityName the name of the city that should be added to profile
   * @return the updated weather profile with the new city added in it.
   */
  @PutMapping(
    path = "/add/id/{id}/city/{cityName}",
    produces = "application/json",
    consumes = "application/json"
  )
  public ResponseEntity<WeatherProfileData> addCity(
      @PathVariable(value = "id")
      @NotBlank(message = "Profile Id " + "can't be null")
      @Pattern(regexp = "^[0-9]+$", message = "Invalid profile Id")
      final String id,
      @PathVariable(value = "cityName") @NotBlank(message = "City name can't be null")
      final String cityName) {
    WeatherProfileData weatherProfileDetails = weatherProfileService.addCity(id, cityName);
    return new ResponseEntity<WeatherProfileData>(weatherProfileDetails, HttpStatus.OK);
  }

  /**
   * Deletes a city in the given weather profile
   *
   * @param id       the id of the weather profile from which the city should be deleted
   * @param cityName - the name of the city which should be deleted.
   * @return the updated Weather profile data after removing the city
   */
  @PutMapping(
    path = "/delete/id/{id}/city/{cityName}",
    produces = "application/json",
    consumes = "application/json"
  )
  public ResponseEntity<WeatherProfileData> deleteCity(
      @PathVariable(value = "id")
      @NotBlank(message = "Profile Id can't be null")
      @Pattern(regexp = "^[0-9]+$", message = "Invalid profile Id")
      final String id,
      @PathVariable(value = "cityName") final String cityName) {
    WeatherProfileData weatherProfileDetails = weatherProfileService.deleteCity(id, cityName);
    return new ResponseEntity<WeatherProfileData>(weatherProfileDetails, HttpStatus.OK);
  }

  /**
   * Updates weather profile name.
   *
   * @param id   the id of the weather profile
   * @param name the new name for the weather profile
   * @return the updated Weather profile data after removing the city
   */
  @PutMapping(
    path = "/update/id/{id}/name/{name}",
    produces = "application/json",
    consumes = "application/json"
  )
  public ResponseEntity<WeatherProfileData> updateProfileName(
      @PathVariable(value = "id")
      @NotBlank(message = "Profile Id can't be null")
      @Pattern(regexp = "^[0-9]+$", message = "Invalid profile Id")
      final String id,
      @PathVariable(value = "name") final String name) {
    WeatherProfileData weatherProfileDetails = weatherProfileService.updateProfileName(id, name);
    return new ResponseEntity<WeatherProfileData>(weatherProfileDetails, HttpStatus.OK);
  }

  /**
   * Gets all the weather profiles with the current city weather data for a given user id.
   *
   * @param userId the user id for which weather data should be retrieved.
   * @return the complete weather profile with current weather data for all the configured cities
   */
  @GetMapping(path = "/user/{userId}", produces = "application/json")
  public ResponseEntity<List<WeatherProfileResponse>> getUserProfiles(
      @PathVariable(value = "userId")
      @NotBlank(message = "User Id can't be null")
      @Pattern(regexp = "^[0-9]+$", message = "Invalid User Id")
      final String userId) {
    List<WeatherProfileResponse> weatherProfileResponseList =
        weatherProfileService.getUserProfiles(userId);
    return new ResponseEntity<>(weatherProfileResponseList, HttpStatus.OK);
  }

    /**
     * Gets the current city weather data for a given user id and profile id.
     *
     * @param userId the user id for which weather data should be retrieved.
     * @param profileId the profile id for which weather data should be retrieved.
     * @return the complete weather profile with current weather data for the given user id and profile id.
     */
    @GetMapping(path = "/user/{userId}/id/{profileId}", produces = "application/json")
    public ResponseEntity<WeatherProfileResponse> getUserProfile(
            @PathVariable(value = "userId")
            @NotBlank(message = "User Id can't be null")
            @Pattern(regexp = "^[0-9]+$", message = "Invalid User Id")
            final String userId,
            @PathVariable(value = "profileId")
            @NotBlank(message = "Profile Id can't be null")
            @Pattern(regexp = "^[0-9]+$", message = "Invalid profile Id")
            final String profileId) {
        WeatherProfileResponse weatherProfileResponse =
                weatherProfileService.getUserProfile(userId,profileId);
        return new ResponseEntity<>(weatherProfileResponse, HttpStatus.OK);
    }
}
