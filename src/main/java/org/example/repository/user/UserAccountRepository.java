package org.example.repository.user;

import java.util.Optional;
import org.example.entities.user.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserAccountRepository extends JpaRepository<UserAccount, Long> {
  Optional<UserAccount> findByPhone(String phone);

  Optional<UserAccount> findUserAccountByPhoneAndPasswordHash(String phone, byte[] hash);
}
