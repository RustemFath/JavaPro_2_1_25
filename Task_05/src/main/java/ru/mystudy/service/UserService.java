package ru.mystudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mystudy.entity.Product;
import ru.mystudy.entity.User;
import ru.mystudy.enums.ProductType;
import ru.mystudy.repository.ProductRepository;
import ru.mystudy.repository.UserRepository;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserService implements CommandLineRunner {
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public User findById(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        return userRepository.findById(id).orElse(null);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    @Transactional
    public User createUser(User user) {
        this.checkUser(user);
        User user1 = userRepository.save(user);
        if (user.getProducts() != null && !user.getProducts().isEmpty()) {
            for (Product product : user.getProducts()) {
                productRepository.save(product);
            }
        }
        return user1;
    }

    @Transactional
    public User updateUser(User user) {
        this.checkUser(user);
        User user1 = userRepository.save(user);
        if (user.getProducts() != null && !user.getProducts().isEmpty()) {
            for (Product product : user.getProducts()) {
                productRepository.save(product);
            }
        }
        return user1;
    }

    @Transactional
    public void deleteUser(User user) {
        this.checkUser(user);
        List<Product> products = this.findByUserId(user.getId());
        if (products != null && products.size() > 0) {
            for (Product product : user.getProducts()) {
                productRepository.delete(product);
            }
        }
        user.setProducts(Collections.emptySet());
        userRepository.delete(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new IllegalArgumentException("Parameter user is null");
        }
    }

    public Product findByProductId(Long id) {
        if (id == null) {
            throw new IllegalArgumentException("Parameter id is null");
        }
        return productRepository.findById(id).orElse(null);
    }

    public List<Product> findByUserId(Long userId) {
        return productRepository.findByUserId(userId);
    }

    @Override
    public void run(String... args) {
        System.out.println("***** User actions ******");

        //create
        User userV = new User("Vladimir", null);
        Product product1 = new Product(userV, "111222333", BigDecimal.valueOf(12.43), ProductType.ACCOUNT);
        Product product2 = new Product(userV, "552622373456342", BigDecimal.valueOf(42.13), ProductType.CARD);
        userV.setProducts(Set.of(product1, product2));
        User user = this.createUser(userV);
        System.out.println("create user: " + user);

        //update
        user.setUsername("Vlad");
        User user1 = this.updateUser(user);
        System.out.println("update user: " + user1);

        List<Product> productList = this.findByUserId(user1.getId());
        System.out.println("product list of user: " + user1 + "\n" + productList);

        //create
        User userP = new User("Peter", null);
        product1 = new Product(userP, "5526223734", BigDecimal.valueOf(56.39), ProductType.ACCOUNT);
        userP.setProducts(Set.of(product1));
        user = this.createUser(userP);
        System.out.println("create user: " + user);

        //get one
        User userOne = this.findById(user.getId());
        System.out.println("find by id user: " + userOne);

        //get product
        Product byProductId = this.findByProductId(product1.getId());
        System.out.println("find by product id: " + byProductId);

        //get all
        List<User> users = this.findAll();
        System.out.println("find all users: " + users);

        this.deleteUser(user1);
        System.out.println("delete user: " + user1);

        //delete
        if (userOne != null) {
            this.deleteUser(userOne);
            System.out.println("delete user: " + userOne);
        };

        //get all
        users = this.findAll();
        System.out.println("find all users: " + users);
    }
}
