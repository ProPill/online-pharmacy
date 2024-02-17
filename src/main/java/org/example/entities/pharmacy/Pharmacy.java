package org.example.entities.pharmacy;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.order.Orders;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "pharmacy")
public class Pharmacy {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "name", nullable = false)
  private String name;

  @Column(name = "address", nullable = false)
  private String address;

  @Column(name = "work_time", nullable = false)
  private String workTime;

  @Column(name = "phone", nullable = false)
  private String phone;

  @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
  private List<Orders> orders = new ArrayList<>();

  @OneToMany(mappedBy = "pharmacy", cascade = CascadeType.ALL)
  private List<PharmacyToItem> pharmacyToItems = new ArrayList<>();
}
