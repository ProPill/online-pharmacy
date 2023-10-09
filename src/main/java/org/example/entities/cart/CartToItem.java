package org.example.entities.cart;

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
@Table(name = "cart_to_item")
public class CartToItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // cart id

    // item id

    @Column
    private Integer quantity;
}
