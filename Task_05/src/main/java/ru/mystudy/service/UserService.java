package ru.mystudy.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;
import ru.mystudy.dto.User;
import ru.mystudy.repository.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements CommandLineRunner {
    private final UserRepository userRepository;

    public Optional<User> findById(Long id) {
        if (id == null) {
            throw new NullPointerException("Parameter id is null");
        }
        return userRepository.findById(id);
    }

    public List<User> findAll() {
        return userRepository.findAll();
    }

    public User createUser(User user) {
        this.checkUser(user);
        return userRepository.save(user);
    }

    public User updateUser(User user) {
        this.checkUser(user);
        return userRepository.save(user);
    }

    public void deleteUser(User user) {
        this.checkUser(user);
        userRepository.delete(user);
    }

    private void checkUser(User user) {
        if (user == null) {
            throw new NullPointerException("Parameter user is null");
        }
    }

    @Override
    public void run(String... args) {
        System.out.println("***** User actions ******");

        //create
        User user = this.createUser(new User("Vladimir"));
        System.out.println("create user: " + user);

        //update
        user.setUsername("Vlad");
        User user1 = this.updateUser(user);
        System.out.println("update user: " + user1);

        //create
        user = this.createUser(new User("Peter"));
        System.out.println("create user: " + user);

        //get one
        Optional<User> userOne = this.findById(user.getId());
        System.out.println("find by id user: " + userOne);

        //get all
        List<User> users = this.findAll();
        System.out.println("find all users: " + users);

        this.deleteUser(user1);
        System.out.println("delete user: " + user1);

        //delete
        userOne.ifPresent(user2 -> {
            this.deleteUser(user2);
            System.out.println("delete user: " + user2);
        });

        //get all
        users = this.findAll();
        System.out.println("find all users: " + users);
    }
}
