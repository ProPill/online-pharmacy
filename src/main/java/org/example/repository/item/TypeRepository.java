package org.example.repository.item;

import java.util.Optional;
import org.example.entities.item.Type;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TypeRepository extends JpaRepository<Type, Long> {
  Optional<Type> findByName(String name);
}
