package org.example.entities.user;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.cart.Cart;
import org.example.entities.order.Orders;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "user_account")
public class UserAccount {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long id;

  @Column(name = "full_name", nullable = false)
  private String fullName;

  @Column(unique = true, nullable = false)
  private String phone;

  @Column(name = "password_hash", nullable = false)
  private byte[] passwordHash;

  @ManyToOne
  @JoinColumn(name = "role_id", nullable = false)
  private Role role;

  @ManyToOne
  @JoinColumn(name = "speciality_id", nullable = true)
  private Speciality speciality;

  @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
  private List<Cart> carts = new ArrayList<>();

  @OneToMany(mappedBy = "userAccount", cascade = CascadeType.ALL)
  private List<Orders> orders = new ArrayList<>();
}
