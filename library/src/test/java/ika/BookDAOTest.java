package ika;

import ika.library.dao.BookDAO;
import ika.library.models.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseBuilder;
import org.springframework.jdbc.datasource.embedded.EmbeddedDatabaseType;

import javax.sql.DataSource;

import java.util.List;
import java.util.UUID;

import static junit.framework.Assert.assertFalse;
import static junit.framework.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;

@ExtendWith(MockitoExtension.class)
public class BookDAOTest {
    private BookDAO bookDAO;
    private JdbcTemplate jdbcTemplate;
    @BeforeEach
    void setUp() {
        DataSource dataSource = new EmbeddedDatabaseBuilder()
                .setType(EmbeddedDatabaseType.H2)
                .setName("testdb" + UUID.randomUUID() + ";MODE=PostgreSQL")
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
    void index_ShouldShowAllBooks() {
        List<Book> books = bookDAO.index();
        assertEquals(2, books.size());
        assertTrue(books.get(0) instanceof Book);
        assertTrue(books.get(1) instanceof Book);

    }

    @Test
    void save_ShouldInsertBook() {
        Book book = new Book("1985", "Оруэлл", 1949);
        bookDAO.save(book);

        Book savedBook = bookDAO.index().stream().filter(b -> b.getTitle().equals(book.getTitle())).findFirst().orElse(null);
        assertEquals("1985", savedBook.getTitle());
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

    @Test
    void show_ShouldShowSpecificBook() {
        Book book = bookDAO.index().stream().filter(b -> b.getId() == 1).findFirst().orElse(null);
        assertEquals("1984", book.getTitle());
    }

    @Test
    void delete_ShouldDeleteBook() {
        bookDAO.delete(1);
        boolean existence = bookDAO.index().stream().anyMatch(b -> b.getId() == 1);
        assertFalse(existence);
    }
}
