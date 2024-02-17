package org.example.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.text.SimpleDateFormat;
import java.util.List;
import org.example.dto.item.ItemDto;
import org.example.dto.pharmacy.PharmacyDto;
import org.example.entities.order.Orders;
import org.example.resources.DateTimeFormatter;

public record OrderDto(
    @JsonProperty("id") Long id,
    @JsonProperty("user_id") Long user_id,
    @JsonProperty("creation_date") String creation_date,
    @JsonProperty("delivery_date") String delivery_date,
    @JsonProperty("sum_price") Double sum_price,
    @JsonProperty("items") List<ItemDto> items,
    @JsonProperty("pharmacy") PharmacyDto pharmacy) {

  public static OrderDto fromOrder(Orders order) {
    SimpleDateFormat formatter = new DateTimeFormatter().getFormatter();
    return new OrderDto(
        order.getId(),
        order.getUserAccount().getId(),
        formatter.format(order.getCreationDate()),
        formatter.format(order.getDeliveryDate()),
        order.getSumPrice(),
        order.getOrderToItems().stream().map(item -> ItemDto.fromItem(item.getItem())).toList(),
        PharmacyDto.fromPharmacy(order.getPharmacy()));
  }
}
