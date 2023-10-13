package org.example.dto.order;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.example.dto.item.ItemDto;
import org.example.entities.order.OrderToItem;


public record OrderToItemDto(
        @JsonProperty("order") OrderDto order,
        @JsonProperty("item") ItemDto item,
        @JsonProperty("quantity") Integer quantity
) {
    public static OrderToItemDto fromOrderToItem(OrderToItem orderToItem) {
        return new OrderToItemDto(
                OrderDto.fromOrder(orderToItem.getOrders()),
                ItemDto.fromItem(orderToItem.getItem()),
                orderToItem.getQuantity()
        );
    }
}
