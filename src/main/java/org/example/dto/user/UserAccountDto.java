package org.example.dto.user;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entities.user.UserAccount;

public record UserAccountDto(
    @JsonProperty("id") Long id,
    @JsonProperty("full_name") String fullName,
    @JsonProperty("phone") String phone,
    @JsonProperty("role") RoleDto role,
    @JsonProperty("speciality") SpecialityDto speciality) {
  public static UserAccountDto fromUserAccount(UserAccount userAccount) {
    return new UserAccountDto(
        userAccount.getId(),
        userAccount.getFullName(),
        userAccount.getPhone(),
        RoleDto.fromRole(userAccount.getRole()),
        SpecialityDto.fromSpeciality(userAccount.getSpeciality()));
        }
}
