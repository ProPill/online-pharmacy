package org.example.repository.cart;

import java.util.Optional;
import org.example.entities.cart.CartToItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CartToItemRepository extends JpaRepository<CartToItem, Long> {

  Optional<CartToItem> getCartToItemByItem_Id(Long item_id);

  Optional<CartToItem> findByItem_IdAndCart_Id(Long item_id, Long cart_id);
}
