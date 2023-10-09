package org.example.entities.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "order_to_item")
public class OrderToItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // order id

    // item id

    @Column
    private Integer quantity;
}
