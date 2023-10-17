package org.example.dto.item;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.dto.user.SpecialityDto;
import org.example.entities.item.Item;

public record ItemDto(
    @JsonProperty("id") Long id,
    @JsonProperty("name") String name,
    @JsonProperty("price") Double price,
    @JsonProperty("manufacturer") String manufacturer,
    @JsonProperty("picture_url") String pictureUrl,
    @JsonProperty("type") TypeDto typeId,
    @JsonProperty("speciality") SpecialityDto speciality) {
  public static ItemDto fromItem(Item item) {
    return new ItemDto(
        item.getId(),
        item.getName(),
        item.getPrice(),
        item.getManufacturer(),
        item.getPictureUrl(),
        TypeDto.fromType(item.getType()),
        SpecialityDto.fromSpeciality(item.getSpeciality()));
  }
}
