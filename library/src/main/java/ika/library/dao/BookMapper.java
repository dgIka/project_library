package ika.library.dao;

import ika.library.models.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class BookMapper implements RowMapper<Book> {
    @Override
    public Book mapRow(ResultSet rs, int rowNum) throws SQLException {
        Book book = new Book();
        book.setId(rs.getInt("book_id"));
        book.setPersonId(rs.getInt("person_id"));
        book.setTitle(rs.getString("title"));
        book.setAuthor(rs.getString("author"));
        book.setYear(rs.getInt("year"));

        try {
            book.setPersonName(rs.getString("person_name"));
        } catch (SQLException e) {
            book.setPersonName(null);
        }
        return book;
    }
}
