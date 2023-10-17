package org.example.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entities.user.Speciality;

public record SpecialityDto(@JsonProperty("id") Long id, @JsonProperty("name") String name) {
  public static SpecialityDto fromSpeciality(Speciality speciality) {
    return new SpecialityDto(speciality.getId(), speciality.getName());
  }
}
