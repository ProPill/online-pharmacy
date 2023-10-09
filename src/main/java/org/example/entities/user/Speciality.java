package org.example.entities.user;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.example.entities.item.Item;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "speciality")
public class Speciality {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL)
    private List<Item> items = new ArrayList<>();

    @OneToMany(mappedBy = "speciality", cascade = CascadeType.ALL)
    private List<UserAccount> userAccounts = new ArrayList<>();
}
