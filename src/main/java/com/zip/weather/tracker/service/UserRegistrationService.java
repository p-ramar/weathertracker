package com.zip.weather.tracker.service;

import com.zip.weather.tracker.entity.UserDetails;
import com.zip.weather.tracker.model.User;
import com.zip.weather.tracker.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

  @Autowired private UserRepository repository;

  /**
   * Registers new user.
   * @param user  the User details that should be registered
   * @return User the updated user details with Id value set in it.
   */
  public User registerUser(User user) {
    UserDetails userDetails =
        UserDetails.builder().name(user.getName()).email(user.getEmail()).build();
    UserDetails updatedDetails = repository.save(userDetails);
    user.setId(updatedDetails.getId());
    return user;
  }
}
