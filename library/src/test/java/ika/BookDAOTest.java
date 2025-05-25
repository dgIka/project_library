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
        // Создаём БД в памяти с таблицами
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
        };
    }

    @Test
    void save_ShouldInsertBook() {
        Book book = new Book("1985", "Оруэлл", 1949);
        bookDAO.save(book); // Сохраняем в H2

        Book savedBook = bookDAO.index().stream().filter(b -> b.getTitle().equals(book.getTitle())).findFirst().orElse(null);
        assertEquals("1985", savedBook.getTitle()); // Проверяем
    }
}
