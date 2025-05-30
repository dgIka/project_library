package ika;

import ika.library.dao.BookMapper;
import ika.library.models.Book;
import org.junit.jupiter.api.Test;

import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static junit.framework.Assert.assertEquals;

public class BookMapperTest {

    @Test
    void shouldMapAllFileds() throws SQLException {
        ResultSet rs = mock(ResultSet.class);
        when(rs.getInt("book_id")).thenReturn(1);
        when(rs.getInt("person_id")).thenReturn(1);
        when(rs.getString("title")).thenReturn("1984");
        when(rs.getString("author")).thenReturn("Оруэлл");
        when(rs.getInt("year")).thenReturn(2018);

        Book book = new BookMapper().mapRow(rs, 1);

        assertEquals(1, book.getId());
        assertEquals(1, book.getPersonId());
        assertEquals("1984", book.getTitle());
        assertEquals("Оруэлл", book.getAuthor());
        assertEquals(2018, book.getYear());
    }
}
