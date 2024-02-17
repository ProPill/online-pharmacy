package org.example.service.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.Optional;
import org.example.entities.user.Role;
import org.example.entities.user.UserAccount;
import org.example.exception.ServerException;
import org.example.repository.user.UserAccountRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {
  @InjectMocks private UserService userService;
  @Mock private UserAccountRepository userAccountRepository;

  private UserAccount expectedUser;

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
  }

  @Test
  void getUserInfoUserNotFound() {
    when(userAccountRepository.findById(any())).thenReturn(Optional.empty());
    ServerException exception =
        assertThrows(ServerException.class, () -> userService.getUserInfo(1L));
    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("USER_NOT_FOUND", exception.getCode());
    assertEquals("USER_NOT_FOUND", exception.getMessage());
  }

  @Test
  void getUserInfo() {
    when(userAccountRepository.findById(any())).thenReturn(Optional.of(expectedUser));
    UserAccount actualUser = userService.getUserInfo(1L);

    assertEquals(expectedUser.getId(), actualUser.getId());
    assertEquals(expectedUser.getRole(), actualUser.getRole());
    assertEquals(expectedUser.getFullName(), actualUser.getFullName());
    assertEquals(expectedUser.getPhone(), actualUser.getPhone());
    assertEquals(expectedUser.getPasswordHash(), actualUser.getPasswordHash());
  }
}
