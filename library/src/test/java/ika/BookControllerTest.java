package ika;

import ika.library.controllers.BookController;
import ika.library.dao.BookDAO;
import ika.library.dao.PersonDAO;
import ika.library.models.Book;
import ika.library.models.Person;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.*;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.*;

public class BookControllerTest {
    private SimpleModel simpleModel;
    private BookDAO mockDAO;
    private PersonDAO mockPersonDAO;
    private BookController bookController;

    @BeforeEach
    public void setUp() {
        simpleModel = new SimpleModel();
        mockPersonDAO = mock(PersonDAO.class);
        doReturn(Collections.singletonList(new Person("Tom", LocalDate.of(1950, 1, 1)))).when(mockPersonDAO).index();
        mockDAO = mock(BookDAO.class);
        Book tempBook = new Book("1984", "Оруэлл", 1949);
        doReturn(tempBook).when(mockDAO).show(0);
        doReturn(Collections.singletonList(tempBook)).when(mockDAO).index();

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

