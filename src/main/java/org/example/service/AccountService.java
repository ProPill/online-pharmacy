package org.example.service;

import static org.example.exception.TypicalServerExceptions.*;

import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Optional;
import java.util.regex.Pattern;
import lombok.RequiredArgsConstructor;
import org.example.entities.user.Role;
import org.example.entities.user.UserAccount;
import org.example.repository.user.RoleRepository;
import org.example.repository.user.UserAccountRepository;
import org.example.security.PasswordEncryption;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncryption encryptor = new PasswordEncryption();

  public UserAccount register(String fullName, String phone, String password) {
    String FIORegex = "[^-А-ЯA-Z\\x27а-яa-z]";
    String phoneNumberRegex = "^\\+7[0-9]{10}$";
    String passwordRegex = "^(([A-z0-9]){6,16})$";

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
    byte[] encryptedPassword = encryptor.getCiphertext(password);
    UserAccount newUser = new UserAccount();
    newUser.setFullName(fullName);
    newUser.setPhone(phone);
    newUser.setPasswordHash(encryptedPassword);
    newUser.setRole(role.get());
    return userAccountRepository.save(newUser);
  }

  public UserAccount login(String phone, String password) throws GeneralSecurityException {
    Optional<UserAccount> user = userAccountRepository.findByPhone(phone);
    if (user.isEmpty()) {
      WRONG_LOGIN_PASSWORD.throwException();
    }
    if (!Arrays.equals(encryptor.getDecrypted(user.get().getPasswordHash()), password.getBytes())) {
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
