package org.example.repository.pharmacy;

import java.util.List;
import org.example.entities.item.Item;
import org.example.entities.pharmacy.Pharmacy;
import org.example.entities.pharmacy.PharmacyToItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PharmacyToItemRepository extends JpaRepository<PharmacyToItem, Long> {}
