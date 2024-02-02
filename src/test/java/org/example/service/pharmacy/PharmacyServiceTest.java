package org.example.service.pharmacy;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import org.springframework.transaction.annotation.Transactional;

@SpringJUnitConfig
@SpringBootTest
@Transactional
class PharmacyServiceTest {

  @Autowired
  private PharmacyService pharmacyService;

  @Test
  void getAll() {}

  @Test
  void getAllByItemId() {}

  @Test
  void testGetAllByItemId_WhenItemNotFound() {
    // Вызываем метод сервиса с несуществующим itemId
    assertThrows(Exception.class, () -> pharmacyService.getAllByItemId(999L));
  }
}
