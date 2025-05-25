package ika;

import ika.library.controllers.BookController;
import ika.library.dao.BookDAO;
import ika.library.dao.PersonDAO;
import ika.library.models.Book;
import ika.library.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.*;

import static junit.framework.Assert.assertEquals;


public class BookControllerTest {
    private SimpleModel simpleModel;
    private BookDAO mockDAO;
    private PersonDAO mockPersonDAO;
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        simpleModel = new SimpleModel();
        mockDAO = new BookDAO(null) {
            @Override
            public List<Book> index() {
                return Collections.singletonList(new Book("1984", "Оруэлл", 1949));
            }
            @Override
            public Book show(int id) {
                return new Book("1984", "Оруэлл", 1949);
            }
        };
        mockPersonDAO = new PersonDAO(null) {
            @Override
            public List<Person> index() {
                return Collections.singletonList(new Person("Tom", LocalDate.of(1950, 1, 1)));
            }
        };
        bookController = new BookController(mockDAO, mockPersonDAO);
    }

    @Test
    void index_ShouldReturnViewAndAddBooksToModel() {
        String viewname = bookController.index(simpleModel);
        assertEquals("books/index", viewname);
        List<Book> books = (List<Book>) simpleModel.getAttribute("books");
        assertEquals(1, books.size());
        assertEquals("1984", books.get(0).getTitle());


    }
     @Test
    void show_ShouldAddTwoAttributesAndReturnView() {
        String viewname = bookController.show(0, simpleModel);
        assertEquals("books/show", viewname);
        Book book = (Book) simpleModel.getAttribute("book");
       List<Person> persons = (List<Person>) simpleModel.getAttribute("people");
        assertEquals("1984", book.getTitle());
        assertEquals(1, persons.size());
        assertEquals("Tom", persons.get(0).getName());
    }
}

