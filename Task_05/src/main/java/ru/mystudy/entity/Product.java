package ru.mystudy.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mystudy.enums.ProductType;

import java.math.BigDecimal;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "account")
    private String account;

    @Column(name = "balance")
    private BigDecimal balance;

    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private ProductType type;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Product(User user, String account, BigDecimal balance, ProductType productType) {
        this.user = user;
        this.account = account;
        this.balance = balance;
        this.type = productType;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", account='" + account + '\'' +
                ", balance=" + balance +
                ", type=" + type +
                ", user_id=" + user.getId() +
                '}';
    }
}
