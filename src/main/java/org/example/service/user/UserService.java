package org.example.service.user;

import lombok.RequiredArgsConstructor;
import org.example.entities.user.UserAccount;
import org.example.repository.user.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

import static org.example.exception.TypicalServerExceptions.*;

@Service
@RequiredArgsConstructor
public class UserService {
  private final UserAccountRepository userAccountRepository;

  public UserAccount getUserInfo(Long user_id) {

    Optional<UserAccount> user = userAccountRepository.findById(user_id);
    if (user.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    return user.get();
  }
}
