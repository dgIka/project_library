package ika.library.dao;


import ika.library.models.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BookDAO {
    private final JdbcTemplate jdbcTemplate;


    @Autowired
    public BookDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Book> index() {
        return jdbcTemplate.query("SELECT * FROM book", new BookMapper());
    }

    public Book show(int id) {
        return jdbcTemplate.query("SELECT book.*, person.name AS person_name FROM book "
                + "LEFT JOIN person ON book.person_id = person.person_id"
                + " WHERE book.book_id = ?"
                ,
                new Object[] { id },
                new BookMapper()).stream().findFirst().orElse(null);
    }

    public List<Book> showBooks(int id) {
        return jdbcTemplate.query("SELECT * FROM book WHERE person_id = ?", new Object[] { id },
                                    new BookMapper());
    }

    public void save(Book book) {
        jdbcTemplate.update("INSERT INTO book(title, author, year) VALUES (?, ?, ?)",
                book.getTitle(),
                book.getAuthor(),
                book.getYear());
    }

    public void update(int id, Book book) {
        jdbcTemplate.update("UPDATE book SET title = ?, author = ?, year = ? WHERE book_id = ?",
                book.getTitle(), book.getAuthor(), book.getYear(), id);
    }

    public void update(int id, Integer person_id) {
        jdbcTemplate.update("UPDATE book SET person_id = ? WHERE book_id = ?", person_id, id);
    }


    public void delete(int id) {
        jdbcTemplate.update("DELETE FROM book WHERE book_id = ?", id);
    }
}
