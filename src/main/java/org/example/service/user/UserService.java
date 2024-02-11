package org.example.service.user;

import static org.example.exception.TypicalServerExceptions.USER_NOT_FOUND;

import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.entities.user.UserAccount;
import org.example.repository.user.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserService {

  private final UserAccountRepository userAccountRepository;

  @Transactional
  public UserAccount getUserInfo(Long user_id) {

    Optional<UserAccount> user = userAccountRepository.findById(user_id);
    if (user.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    return user.get();
  }
}
