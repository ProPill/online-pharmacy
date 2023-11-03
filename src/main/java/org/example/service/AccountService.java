package org.example.service;

import static org.example.exception.TypicalServerExceptions.WRONG_INPUT_DATA;

import java.util.Optional;
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
    if (userAccountRepository.findByPhone(phone).isEmpty()) {
      WRONG_INPUT_DATA.throwException();
    }
    Optional<Role> role = roleRepository.findByName("пользователь");
    byte [] encryptedPassword = encryptor.getCiphertext(password);
    UserAccount newUser = new UserAccount();
    newUser.setFullName(fullName);
    newUser.setPhone(phone);
    newUser.setPasswordHash(encryptedPassword);
    ////TODO: смотрим что с таким номером еще нет пчелов
    return userAccountRepository.save(newUser);
  }

  public UserAccount login(String phone, String password) {
    byte [] encryptedPassword = encryptor.getCiphertext(password);
    if (userAccountRepository.findUserAccountByPhoneAndPasswordHash(phone, encryptedPassword).isEmpty()){

    }



  }

  public UserAccount logout(Long userId) {}
}
