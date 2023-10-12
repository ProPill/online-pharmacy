package org.example.dto.pharmacy;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.entities.pharmacy.Pharmacy;

public record PharmacyDto(
        @JsonProperty("id")	Long id,

        @JsonProperty("name") String name,
        @JsonProperty("address") String address,
        @JsonProperty("work_time") String workTime,
        @JsonProperty("phone") 	String phone

) {
    public static PharmacyDto fromPharmacy(Pharmacy pharmacy) {
        return new PharmacyDto(
                pharmacy.getId(),
                pharmacy.getName(),
                pharmacy.getAddress(),
                pharmacy.getWorkTime(),
                pharmacy.getPhone()
        );
    }
}
