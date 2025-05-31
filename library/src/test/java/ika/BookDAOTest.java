package ika;

import ika.library.dao.BookDAO;
import ika.library.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookDAOTest {
    private BookDAO bookDAO;
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb;MODE=PostgreSQL")
                .addScript("classpath:schema.sql")
                .addScript("classpath:test-data.sql")
                .build();
    jdbcTemplate = new JdbcTemplate(dataSource);
        bookDAO = new BookDAO(new JdbcTemplate(dataSource)) {
            @Override
            public void save(Book book) {
                jdbcTemplate.update("INSERT INTO book(title, author, \"year\") VALUES (?, ?, ?)",
                        book.getTitle(),
                        book.getAuthor(),
                        book.getYear());
            }
            @Override
            public void update(int id, Book book) {
                jdbcTemplate.update("UPDATE book SET title = ?, author = ?, \"year\" = ? WHERE book_id = ?",
                        book.getTitle(),
                        book.getAuthor(),
                        book.getYear(),
                        id);

            }
        };
    }

    @Test
    void save_ShouldInsertBook() {
        Book book = new Book("1985", "Оруэлл", 1949);
        bookDAO.save(book);

        Book savedBook = bookDAO.index().stream().filter(b -> b.getTitle().equals(book.getTitle())).findFirst().orElse(null);
        assertEquals("1985", savedBook.getTitle());
    }

    void indexShouldShowAllBooks() {

    }

    @Test
    void update_ShouldUpdateBook() {
        Book book = new Book("1985", "Оруэлл", 1949);
        bookDAO.save(book);
        Book renewBook = new Book("1986", "Оруэлл", 1949);
        bookDAO.update(1, renewBook);

        Book newBook = bookDAO.index().stream().filter(b -> b.getId() == 1).findFirst().orElse(null);
        assertEquals("1986", newBook.getTitle());

    }
}
