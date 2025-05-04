package ru.mystudy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;

    @OneToMany(fetch = FetchType.EAGER, mappedBy = "user")
    private Set<Product> products;

    public User(Long id, String username, List<Product> products) {
        this.id = id;
        this.username = username;
        this.products = new HashSet<>(0);
        if (products != null) {
            this.products.addAll(products);
        }
    }

    public User(String username, List<Product> products) {
        this(null, username, products);
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", products.size=" + products.size() +
                '}';
    }
}
