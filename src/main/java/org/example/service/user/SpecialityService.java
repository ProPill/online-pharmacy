package org.example.service.user;

import static org.example.exception.TypicalServerExceptions.USER_NOT_DOC;
import static org.example.exception.TypicalServerExceptions.USER_NOT_FOUND;

import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.example.entities.user.Speciality;
import org.example.entities.user.UserAccount;
import org.example.repository.user.SpecialityRepository;
import org.example.repository.user.UserAccountRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SpecialityService {

  private final SpecialityRepository specialityRepository;
  private final UserAccountRepository userAccountRepository;

  @Transactional
  public List<Speciality> getAll() {
    return specialityRepository.findAll();
  }

  @Transactional
  public Speciality getByUserId(Long id) {
    Optional<UserAccount> user = userAccountRepository.findById(id);
    if (user.isEmpty()) {
      USER_NOT_FOUND.throwException();
    }
    if (user.get().getSpeciality() == null) {
      USER_NOT_DOC.throwException();
    }
    return user.get().getSpeciality();
  }
}
