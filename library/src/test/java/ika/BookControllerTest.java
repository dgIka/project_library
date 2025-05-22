package ika;

import ika.library.controllers.BookController;
import ika.library.dao.BookDAO;
import ika.library.models.Book;
import org.junit.jupiter.api.Test;
import org.springframework.ui.Model;

import java.util.*;

import static junit.framework.Assert.assertEquals;


public class BookControllerTest {
    @Test
    void index_ShouldReturnViewAndAddBooksToModel() {
        BookDAO mockDAO = new BookDAO(null) {
            @Override
            public List<Book> index() {
                return Collections.singletonList(new Book("1984", "Оруэлл", 1949));
            }
        };
        BookController bookController = new BookController(mockDAO, null);
        SimpleModel simpleModel = new SimpleModel();

        String viewname = bookController.index(simpleModel);

        assertEquals("books/index", viewname);
        List<Book> books = (List<Book>) simpleModel.getAttribute("books");
        assertEquals(1, books.size());
        assertEquals("1984", books.get(0).getTitle());


    }
}
