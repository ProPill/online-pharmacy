package org.example.service;

import static org.example.exception.TypicalServerExceptions.*;
import static org.example.resources.Patterns.*;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.HexFormat;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.example.entities.user.Role;
import org.example.entities.user.UserAccount;
import org.example.repository.user.RoleRepository;
import org.example.repository.user.UserAccountRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;

  MessageDigest digest;

  {
    try {
      digest = MessageDigest.getInstance("SHA-256");
    } catch (NoSuchAlgorithmException e) {
      throw new RuntimeException(e);
    }
  }

  public UserAccount register(String fullName, String phone, String password) {
    Pattern patternFIO = Pattern.compile(FIORegex);
    Pattern patternNumber = Pattern.compile(phoneNumberRegex);
    Pattern patternPassword = Pattern.compile(passwordRegex);

    if (!patternFIO.matcher(fullName).find()) {
      INVALID_FIO.throwException();
    }

    if (!patternNumber.matcher(phone).find()) {
      INVALID_PHONE.throwException();
    }

    if (!patternPassword.matcher(password).find()) {
      INVALID_PASSWORD.throwException();
    }

    if (userAccountRepository.findByPhone(phone).isPresent()) {
      PHONE_IS_REGISTERED.throwException();
    }
    Optional<Role> role = roleRepository.findByName("пользователь");
    byte[] encryptedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    UserAccount newUser = new UserAccount();
    newUser.setFullName(fullName);
    newUser.setPhone(phone);
    newUser.setPasswordHash(encryptedPassword);
    newUser.setRole(role.get());
    return userAccountRepository.save(newUser);
  }

  public UserAccount login(String phone, String password) {
    Optional<UserAccount> user = userAccountRepository.findByPhone(phone);
    if (user.isEmpty()) {
      WRONG_LOGIN_PASSWORD.throwException();
    }
    byte[] encryptedPassword = digest.digest(password.getBytes(StandardCharsets.UTF_8));
    if (!Arrays.equals(
        HexFormat.of().formatHex(user.get().getPasswordHash()).toCharArray(),
        HexFormat.of().formatHex(encryptedPassword).toCharArray())) {
      WRONG_LOGIN_PASSWORD.throwException();
    }
    return user.get();
  }

  public UserAccount logout(Long userId) {
    Optional<UserAccount> user = userAccountRepository.findById(userId);
    if (user.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    return user.get();
  }
}
