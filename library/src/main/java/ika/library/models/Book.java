package ika.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

public class Book {
    private int id;
    private int personId;
    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 1, max = 30, message = "Should be between 1 and 30 characters")
    private String title;
    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 1, max = 30, message = "Should be between 1 and 30 characters")
    private String author;
    @Min(value = 0, message = "Год не может быть отрицательным")
    private int year;
    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 1, max = 30, message = "Should be between 1 and 30 characters")
    private String personName;

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public Book() {
    }

    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
}
