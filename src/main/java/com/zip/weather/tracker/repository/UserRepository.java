package com.zip.weather.tracker.repository;

import com.zip.weather.tracker.entity.UserDetails;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<UserDetails, Long> {

  Optional<UserDetails> findById(Long id);
}
