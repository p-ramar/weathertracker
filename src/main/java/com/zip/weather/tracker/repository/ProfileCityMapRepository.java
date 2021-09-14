package com.zip.weather.tracker.repository;

import com.zip.weather.tracker.entity.ProfileCityId;
import com.zip.weather.tracker.entity.ProfileCityMap;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProfileCityMapRepository extends CrudRepository<ProfileCityMap, ProfileCityId> {

  ProfileCityMap findByProfileCityId(ProfileCityId profileCityId);
}
