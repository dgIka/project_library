package ika.library.models;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;

public class Person {
    private int id;
    @NotEmpty(message = "Не должно быть пустым")
    @Size(min = 1, max = 30, message = "Should be between 1 and 30 characters")
    private String name;
    private LocalDate birthday;

    public Person() {
    }

    public Person(String name, LocalDate birthday) {

        this.name = name;
        this.birthday = birthday;
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

    public LocalDate getBirthday() {
        return birthday;
    }

    public void setBirthday(LocalDate birthday) {
        this.birthday = birthday;
    }
}
