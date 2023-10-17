package org.example.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entities.item.Type;

@SuppressWarnings("google-java-format")
public record TypeDto(
        @JsonProperty("id") Long id,
        @JsonProperty("id") String name
) {
    public static TypeDto fromType(Type type) {
        return new TypeDto(
                type.getId(),
                type.getName()
        );
    }
}
