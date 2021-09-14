package com.zip.weather.tracker.controller;

import com.zip.weather.tracker.model.User;
import com.zip.weather.tracker.service.UserRegistrationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@Validated
@Slf4j
@RequestMapping("/user")
public class UserController {
  @Autowired UserRegistrationService userService;

  /**
   * Registers new user.
   * @param user  the User details that should be registered
   * @return User the updated user details with Id value set in it.
   */
  @PostMapping(path = "/register", produces = "application/json", consumes = "application/json")
  public ResponseEntity<User> registerUser(@RequestBody @Valid User user) {
    log.info("Registering user:"+user.toString());
    User userDetails = userService.registerUser(user);
    return new ResponseEntity<User>(userDetails, HttpStatus.OK);
  }
}
