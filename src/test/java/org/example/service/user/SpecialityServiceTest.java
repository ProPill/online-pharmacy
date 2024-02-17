package org.example.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.example.entities.user.Role;
import org.example.entities.user.Speciality;
import org.example.entities.user.UserAccount;
import org.example.exception.ServerException;
import org.example.repository.user.SpecialityRepository;
import org.example.repository.user.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class SpecialityServiceTest {
  @InjectMocks private SpecialityService specialityService;
  @Mock private UserAccountRepository userAccountRepository;
  @Mock private SpecialityRepository specialityRepository;

  private UserAccount expectedUser;
  private UserAccount userWithSpeciality;
  private Speciality speciality;
  private List<Speciality> specialityList;

  @BeforeEach
  void setUp() {
    Role expectedRole = new Role();
    expectedRole.setId(-1L);
    expectedRole.setName("пользователь");

    expectedUser = new UserAccount();
    expectedUser.setRole(expectedRole);
    expectedUser.setPhone("+79290367458");
    expectedUser.setFullName("Александра Лысенко");
    expectedUser.setId(null);

    speciality = new Speciality();
    speciality.setName("терапевт");
    speciality.setId(1L);

    Speciality anotherSpeciality = new Speciality();
    anotherSpeciality.setName("онколог");
    anotherSpeciality.setId(2L);
    specialityList = Arrays.asList(speciality, anotherSpeciality);
    userWithSpeciality = new UserAccount();
    userWithSpeciality.setRole(expectedRole);
    userWithSpeciality.setPhone("+79290367458");
    userWithSpeciality.setFullName("Александра Лысенко");
    userWithSpeciality.setSpeciality(speciality);
  }

  @Test
  void getAll() {
    when(specialityRepository.findAll()).thenReturn(specialityList);

    List<Speciality> expectedSpecialities = specialityService.getAll();

    assertEquals(specialityList, expectedSpecialities);
  }

  @Test
  void getByUserIdUserNotFound() {
    when(userAccountRepository.findById(any())).thenReturn(Optional.empty());

    ServerException exception =
        assertThrows(ServerException.class, () -> specialityService.getByUserId(1L));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("USER_NOT_FOUND", exception.getCode());
    assertEquals("USER_NOT_FOUND", exception.getMessage());
  }

  @Test
  void getByUserIdUserNotDoc() {
    when(userAccountRepository.findById(any())).thenReturn(Optional.of(expectedUser));

    ServerException exception =
        assertThrows(ServerException.class, () -> specialityService.getByUserId(1L));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("USER_NOT_DOC", exception.getCode());
    assertEquals("USER_NOT_DOC", exception.getMessage());
  }

  @Test
  void getByUserId() {
    when(userAccountRepository.findById(any())).thenReturn(Optional.of(userWithSpeciality));

    Speciality actualSpeciality = specialityService.getByUserId(1L);

    assertEquals(speciality.getId(), actualSpeciality.getId());
    assertEquals(speciality.getName(), actualSpeciality.getName());
  }
}
