package org.example.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.cart.Cart;
import org.example.entities.order.Orders;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;

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

    @Column
    private String fullName;

    @Column(unique = true)
    private String phone;

    @Column
    private String passwordHash;

    @ManyToOne
    @JoinColumn(name = "role_id", nullable = false)
    private Role role;

    @ManyToOne
    @JoinColumn(name = "speciality_id", nullable = false)
    private Speciality speciality;

    @OneToMany(mappedBy = "userAccount", cascade = ALL)
    private List<Cart> carts = new ArrayList<>();

    @OneToMany(mappedBy = "userAccount", cascade = ALL)
    private List<Orders> orders = new ArrayList<>();
}
