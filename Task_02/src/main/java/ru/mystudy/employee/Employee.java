package ru.mystudy.employee;

import java.util.Objects;

public class Employee {
    private final String name;
    private final int age;
    private final PositionEnum position;

    public Employee(String name, int age, PositionEnum position) {
        if (Objects.isNull(name) || name.isEmpty()) {
            throw new IllegalArgumentException("Parameter name is null or empty");
        }
        if (Objects.isNull(position)) {
            throw new IllegalArgumentException("Parameter position is null");
        }
        if (age < 1) {
            throw new IllegalArgumentException("Parameter age must be more zero");
        }
        this.name = name;
        this.age = age;
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public PositionEnum getPosition() {
        return position;
    }
}
