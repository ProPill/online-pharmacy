package org.example.repository.order;

import org.example.entities.order.OrderToItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderToItemRepository extends JpaRepository<OrderToItem, Long> {

}
