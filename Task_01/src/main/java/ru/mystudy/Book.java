package ru.mystudy;

import ru.mystudy.annotations.AfterSuite;
import ru.mystudy.annotations.BeforeSuite;
import ru.mystudy.annotations.Test;

import java.util.Objects;

public class Book {
    private final String name;
    private final String author;

    public Book(String name, String author) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Empty name of book");
        }
        if (Objects.isNull(author) || author.isEmpty()) {
            throw new IllegalArgumentException("Empty author of book");
        }
        this.name = name;
        this.author = author;
    }

    public Book() {
        this.name = "default Name";
        this.author = "default Author";
    }

    public void write(String s) {
        System.out.println("write to book string = " + s);
    }

    @Test
    public String read() {
        System.out.println("read from book");
        return "hello, i am book";
    }

    @BeforeSuite
    public static void open() {
        System.out.println("open book");
    }

    @AfterSuite
    public static void close() {
        System.out.println("close book");
    }

    @Test(priority = 1)
    public String getName() {
        System.out.println("getName = " + name);
        return name;
    }

    @Test(priority = 2)
    public String getAuthor() {
        System.out.println("getAuthor = " + author);
        return author;
    }
}
