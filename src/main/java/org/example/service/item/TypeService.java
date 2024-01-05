package org.example.service.item;

import java.util.List;
import lombok.RequiredArgsConstructor;
import org.example.entities.item.Type;
import org.example.repository.item.TypeRepository;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TypeService {

  private final TypeRepository typeRepository;

  public List<Type> getAll() {
    return typeRepository.findAll();
  }
}
