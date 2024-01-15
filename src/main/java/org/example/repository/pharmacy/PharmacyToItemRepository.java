package org.example.repository.pharmacy;

import org.example.entities.item.Item;
import org.example.entities.pharmacy.PharmacyToItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PharmacyToItemRepository extends JpaRepository<PharmacyToItem, Long> {
    PharmacyToItem findAllByPharmacyIdAndItemId(Long pharmacyId, Long itemId);
}
