package org.example.entities.order;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.item.Item;

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

  @ManyToOne
  @JoinColumn(name = "order_id", nullable = false)
  private Orders orders;

  @ManyToOne
  @JoinColumn(name = "item_id", nullable = false)
  private Item item;

  private Integer quantity;
}
