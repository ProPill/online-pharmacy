package org.example.repository.pharmacy;

import org.example.entities.pharmacy.PharmacyToItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyToItemRepository extends JpaRepository<PharmacyToItem, Long> {
  PharmacyToItem findAllByPharmacyIdAndItemId(Long pharmacyId, Long itemId);
}
