package org.example.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entities.user.Role;

public record RoleDto(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
    public static RoleDto fromRole(Role role) {
        return new RoleDto(
                role.getId(),
                role.getName()
        );
    }
}
