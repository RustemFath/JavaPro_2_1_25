package ru.mystudy;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import ru.mystudy.dto.User;
import ru.mystudy.service.UserService;

import java.util.List;

@ComponentScan
public class MainApp {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(MainApp.class);
        UserService userService = context.getBean(UserService.class);
        System.out.println("***** User actions ******");

        //create
        User user = userService.createUser(new User("Vladimir"));
        System.out.println("create user: " + user);

        //update
        user.setUsername("Vlad");
        user = userService.updateUser(user);
        System.out.println("update user: " + user);

        //create
        user = userService.createUser(new User("Peter"));
        System.out.println("create user: " + user);

        //get one
        User userOne = userService.findById(user.getId());
        System.out.println("find by id user: " + userOne);

        //get all
        List<User> users = userService.findAll();
        System.out.println("find all users: " + users);

        //delete
        userService.deleteUser(userOne);
        System.out.println("delete user: " + userOne);

        //get all
        users = userService.findAll();
        System.out.println("find all users: " + users);
    }
}
