package org.example.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entities.item.Type;

public record TypeDto(@JsonProperty("id") Long id, @JsonProperty("name") String name) {

  public static TypeDto fromType(Type type) {
    return new TypeDto(type.getId(), type.getName());
  }
}
