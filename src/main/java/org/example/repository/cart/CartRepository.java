package org.example.repository.cart;

import java.util.Optional;
import org.example.entities.cart.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
  Optional<Cart> findByUserAccount_Id(Long user_id);
}
