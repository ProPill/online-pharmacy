package org.example.service.item;

import lombok.RequiredArgsConstructor;
import org.example.entities.item.Type;
import org.example.repository.item.TypeRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TypeService {

  private final TypeRepository typeRepository;

  public List<Type> getAll() {
    return typeRepository.findAll();
  }
}
