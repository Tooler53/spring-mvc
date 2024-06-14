package ru.yarilin.springcourse.models.project1;

import java.util.ArrayList;
import java.util.List;

public class Person {
    private int id;

    private String fio;

    private int age;

    private List<Book> bookList = new ArrayList<>();

    public Person() {
    }

    public Person(String fio, int age) {
        this.fio = fio;
        this.age = age;
    }

    public Person(int id, String fio, int age) {
        this(fio, age);
        this.id = id;
    }

    public String getFio() {
        return fio;
    }

    public void setFio(String fio) {
        this.fio = fio;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<Book> getBookList() {
        return bookList;
    }

    public void setBookList(List<Book> bookList) {
        this.bookList = bookList;
    }
}
