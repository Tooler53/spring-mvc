package ru.yarilin.springcourse.models.project1;

import java.util.Optional;

public class Book {

    private int id;

    private String name;

    private String author;

    private int year;

    private int person_id;

    private Person person;

    public Book() {
    }

    public Book(String name, String author, int year, int person_id) {
        this.name = name;
        this.author = author;
        this.year = year;
        this.person_id = person_id;
    }

    public Book(int id, String name, String author, int year, int person_id) {
        this(name, author, year, person_id);
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getPersonId() {
        return person_id;
    }

    public void setPersonId(int person_id) {
        this.person_id = person_id;
    }

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }
}
