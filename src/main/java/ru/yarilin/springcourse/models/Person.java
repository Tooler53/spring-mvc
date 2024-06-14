package ru.yarilin.springcourse.models;

import jakarta.validation.constraints.*;

public class Person {
    private int id;

    @NotEmpty(message = "Name should not be empty")
    @Size(min = 2, max = 30, message = "Not valid name")
    private String name;

    @Min(value = 0, message = "too small age")
    private int age;

    @NotEmpty(message = "Email should not be empty")
    @Email(message = "Email should not be valid")
    private String email;

    //Страна, Город, индекс (6 цифр)
    @Pattern(regexp = "([A-Z]\\w+,\\s){2}\\d{6}", message = "Your address should be in this format: Country, City, Postal code(6 digit)")
    private String address;

    public Person() {
    }

    public Person(String name, int age, String email, String address) {
        this.name = name;
        this.age = age;
        this.email = email;
        this.address = address;
    }

    public Person(int id, String name, int age, String email, String address) {
        this(name, age, email, address);
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
