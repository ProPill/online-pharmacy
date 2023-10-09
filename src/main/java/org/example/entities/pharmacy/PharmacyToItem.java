package org.example.entities.pharmacy;

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
@Table(name = "pharmacy_to_item")
public class PharmacyToItem {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // pharmacy id

    // item id

    @Column
    private Integer quantity;
}
