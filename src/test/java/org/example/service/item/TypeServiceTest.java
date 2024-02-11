package org.example.service.item;

import org.example.repository.item.TypeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class TypeServiceTest {

  @Mock
  private TypeRepository typeRepository;

  @InjectMocks
  private TypeService typeService;

  @BeforeEach
  void setUp() {
  }

  @Test
  void getAll() {
  }
}