package org.example.service;

import java.util.Optional;
import java.util.regex.Pattern;

import lombok.RequiredArgsConstructor;
import org.example.entities.user.Role;
import org.example.entities.user.UserAccount;
import org.example.repository.user.RoleRepository;
import org.example.repository.user.UserAccountRepository;
import org.example.security.PasswordEncryption;
import org.springframework.stereotype.Service;

import static org.example.exception.TypicalServerExceptions.*;

@Service
@RequiredArgsConstructor
public class AccountService {
  private final UserAccountRepository userAccountRepository;
  private final RoleRepository roleRepository;

  private final PasswordEncryption encryptor = new PasswordEncryption();

  public UserAccount register(String fullName, String phone, String password) {
    String FIORegex = "^[А-ЯЁ][а-яё]*$";
    String phoneNumberRegex = "^((\\+7)+([0-9]){10})$";
    String passwordRegex = "^(([A-z0-9]){6,16})$";

    Pattern patternFIO = Pattern.compile(FIORegex);
    Pattern patternNumber=Pattern.compile(phoneNumberRegex);
    Pattern patternPassword = Pattern.compile(passwordRegex);

    if (userAccountRepository.findByPhone(phone).isEmpty()) {
      PHONE_IS_REGISTERED.throwException();
    }
    ////TODO: смотрим что введённые параметры не превышают лимиты, если хоть один превышает, то WRONG INPUT
    Optional<Role> role = roleRepository.findByName("пользователь");
    byte [] encryptedPassword = encryptor.getCiphertext(password);
    UserAccount newUser = new UserAccount();
    newUser.setFullName(fullName);
    newUser.setPhone(phone);
    newUser.setPasswordHash(encryptedPassword);
    return userAccountRepository.save(newUser);
  }

  public UserAccount login(String phone, String password) {
    byte [] encryptedPassword = encryptor.getCiphertext(password);
    Optional<UserAccount> user = userAccountRepository.findUserAccountByPhoneAndPasswordHash(phone, encryptedPassword);
    if (user.isEmpty()){
      WRONG_LOGIN_PASSWORD.throwException();
    }
    return user.get();
  }

  public UserAccount logout(Long userId) {
    
  }
}
