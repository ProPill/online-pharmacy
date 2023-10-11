package org.example.repository.cart;

import org.example.entities.cart.CartToItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartToItemRepository extends JpaRepository<CartToItem, Long> {
}
