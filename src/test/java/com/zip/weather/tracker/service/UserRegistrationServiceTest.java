package com.zip.weather.tracker.service;

import com.zip.weather.tracker.entity.UserDetails;
import com.zip.weather.tracker.model.User;
import com.zip.weather.tracker.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserRegistrationServiceTest {
  @InjectMocks private UserRegistrationService userRegistrationService;
  @Mock private UserRepository userRepositoryMock;
  @Mock private UserDetails userDetailsMock;
  @Mock private User userMock;

  @BeforeEach
  public void setUp() throws Exception {
    openMocks(this);
  }

  @Test
  public void testRegisterUser_validInput() {
    when(userRepositoryMock.save(Mockito.any(UserDetails.class))).thenReturn(userDetailsMock);
    userRegistrationService.registerUser(userMock);
    Mockito.verify(userMock, times(1)).setId(Mockito.anyLong());
  }

  @Test
  public void testRegisterUser_InvalidInput_Should_Throw_Exception() {
    assertThrows(NullPointerException.class, () -> userRegistrationService.registerUser(null));
  }
}
