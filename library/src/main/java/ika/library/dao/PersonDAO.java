package ika.library.dao;

import ika.library.models.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
public class PersonDAO {
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public PersonDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Person> index() {
        return jdbcTemplate.query("SELECT * FROM person", new PersonMapper());
    }

    public Person show(int id) {
        return jdbcTemplate.query("SELECT * FROM person WHERE person_id = ?",
                new Object[] { id },
                new PersonMapper()).stream().findFirst().orElse(null);
    }

    public void save(Person person) {
        jdbcTemplate.update("INSERT INTO person(name, birth_date) VALUES (?, ?)",
                person.getName(),
                person.getBirthday() != null ?
                        java.sql.Date.valueOf(person.getBirthday()) : null);
    }

    public void update(int id, Person person) {
        jdbcTemplate.update("UPDATE person SET name = ?, birth_date = ? WHERE person_id = ?",
                person.getName(),
                person.getBirthday(),
                id);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM person WHERE person_id = ?", id);
    }
}
