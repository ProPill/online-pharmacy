package org.example.service.account;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Optional;
import org.example.entities.cart.Cart;
import org.example.entities.user.Role;
import org.example.entities.user.UserAccount;
import org.example.exception.ServerException;
import org.example.repository.user.RoleRepository;
import org.example.repository.user.UserAccountRepository;
import org.example.service.AccountService;
import org.example.service.cart.CartService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
  @Mock private UserAccountRepository userAccountRepository;

  @Mock private RoleRepository roleRepository;

  @InjectMocks private AccountService accountService;

  @Mock private CartService cartService;

  private UserAccount expectedUser;
  private Role expectedRole;
  private Cart expectedCart;

  MessageDigest digest;

  {
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  @BeforeEach
  void setUp() {
    expectedRole = new Role();
    expectedRole.setId(-1L);
    expectedRole.setName("пользователь");

    expectedUser = new UserAccount();
    expectedUser.setRole(expectedRole);
    expectedUser.setPhone("+79290367458");
    expectedUser.setFullName("Александра Лысенко");
    expectedUser.setId(null);
    expectedUser.setPasswordHash(digest.digest("123456".getBytes(StandardCharsets.UTF_8)));

    expectedCart = new Cart();
    expectedCart.setId(null);
    expectedCart.setUserAccount(expectedUser);
  }

  @Test
  void registerInvalidFIO() {
    ServerException exception =
        assertThrows(
            ServerException.class, () -> accountService.register("sxdcvb", "sdfvb", "sdfg"));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("INVALID_FIO", exception.getCode());
    assertEquals("INVALID_FIO", exception.getMessage());
  }

  @Test
  void registerInvalidNumber() {
    ServerException exception =
        assertThrows(
            ServerException.class,
            () -> accountService.register("Александра Лысенко", "sdfvb", "sdfg"));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("INVALID_PHONE", exception.getCode());
    assertEquals("INVALID_PHONE", exception.getMessage());
  }

  @Test
  void registerInvalidPassword() {
    ServerException exception =
        assertThrows(
            ServerException.class,
            () -> accountService.register("Александра Лысенко", "+79290367458", "sdfg"));
    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("INVALID_PASSWORD", exception.getCode());
    assertEquals("INVALID_PASSWORD", exception.getMessage());
  }

  @Test
  void registerPhoneAlreadyRegistered() {
    when(userAccountRepository.findByPhone("+79290367458")).thenReturn(Optional.of(expectedUser));
    ServerException exception =
        assertThrows(
            ServerException.class,
            () -> accountService.register("Александра Лысенко", "+79290367458", "sdfgsw2"));
    assertEquals(HttpStatus.CONFLICT, exception.getStatus());
    assertEquals("PHONE_IS_REGISTERED", exception.getCode());
    assertEquals("PHONE_IS_REGISTERED", exception.getMessage());
  }

  @Test
  void register() {
    when(userAccountRepository.findByPhone("+79290367458")).thenReturn(Optional.empty());
    when(roleRepository.findByName("пользователь")).thenReturn(Optional.of(expectedRole));
    when(userAccountRepository.save(any())).thenReturn(expectedUser);
    when(cartService.addUserCart(any())).thenReturn(expectedCart);

    UserAccount actualUser =
        accountService.register("Александра Лысенко", "+79290367458", "123456");
    assertEquals(expectedUser.getId(), actualUser.getId());
    assertEquals(expectedUser.getSpeciality(), actualUser.getSpeciality());
    assertEquals(expectedUser.getRole(), actualUser.getRole());
    assertEquals(expectedUser.getFullName(), actualUser.getFullName());
    assertEquals(expectedUser.getPhone(), actualUser.getPhone());
    assertEquals(expectedUser.getPasswordHash(), actualUser.getPasswordHash());
  }

  @Test
  void loginWrongPhone() {
    when(userAccountRepository.findByPhone("+79290367458")).thenReturn(Optional.empty());

    ServerException exception =
        assertThrows(ServerException.class, () -> accountService.login("+79290367458", "123456"));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("WRONG_LOGIN_PASSWORD", exception.getCode());
    assertEquals("WRONG_LOGIN_PASSWORD", exception.getMessage());
  }

  @Test
  void loginWrongPassword() {
    when(userAccountRepository.findByPhone("+79290367458")).thenReturn(Optional.of(expectedUser));

    ServerException exception =
        assertThrows(ServerException.class, () -> accountService.login("+79290367458", "12345"));

    assertEquals(HttpStatus.BAD_REQUEST, exception.getStatus());
    assertEquals("WRONG_LOGIN_PASSWORD", exception.getCode());
    assertEquals("WRONG_LOGIN_PASSWORD", exception.getMessage());
  }

  @Test
  void login() {
    when(userAccountRepository.findByPhone("+79290367458")).thenReturn(Optional.of(expectedUser));

    UserAccount actualUser = accountService.login("+79290367458", "123456");

    assertEquals(expectedUser.getId(), actualUser.getId());
    assertEquals(expectedUser.getRole(), actualUser.getRole());
    assertEquals(expectedUser.getFullName(), actualUser.getFullName());
    assertEquals(expectedUser.getPhone(), actualUser.getPhone());
    assertEquals(expectedUser.getPasswordHash(), actualUser.getPasswordHash());
  }

  @Test
  void logoutUserNotFound() {
    when(userAccountRepository.findById(any())).thenReturn(Optional.empty());
    ServerException exception =
        assertThrows(ServerException.class, () -> accountService.logout(1L));

    assertEquals(HttpStatus.NOT_FOUND, exception.getStatus());
    assertEquals("USER_NOT_FOUND", exception.getCode());
    assertEquals("USER_NOT_FOUND", exception.getMessage());
  }

  @Test
  void logout() {
    when(userAccountRepository.findById(any())).thenReturn(Optional.of(expectedUser));

    UserAccount actualUser = accountService.logout(1L);

    assertEquals(expectedUser.getId(), actualUser.getId());
    assertEquals(expectedUser.getRole(), actualUser.getRole());
    assertEquals(expectedUser.getFullName(), actualUser.getFullName());
    assertEquals(expectedUser.getPhone(), actualUser.getPhone());
    assertEquals(expectedUser.getPasswordHash(), actualUser.getPasswordHash());
  }
}
