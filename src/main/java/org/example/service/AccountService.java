package org.example.service;

import static org.example.exception.TypicalServerExceptions.WRONG_INPUT_DATA;

import java.util.Optional;
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

  public UserAccount register(String fullName, String phone, String password) {
    if (userAccountRepository.findByPhone(phone).isEmpty()) {
      WRONG_INPUT_DATA.throwException();
    }
    Optional<Role> role = roleRepository.findByName("пользователь");
    ///TODO: зашифровать пароль и положить в переменную password
    UserAccount newUser = new UserAccount();
    newUser.setFullName(fullName);
    newUser.setPhone(phone);
    newUser.setPasswordHash(password);
    return userAccountRepository.save(newUser);
  }

  public UserAccount login(String phone, String password) {}

  public UserAccount logout(Long userId) {}
}
