package org.example.repositories;

import org.example.exception.EntityException;
import org.example.models.entities.Book;

import java.sql.*;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book> implements BookRepository {


    public BookRepositoryImpl() {
        super("BOOK", "BOOK_ID");
    }

    @Override
    protected Book buildEntity(ResultSet rs){

        try {
            return Book.builder()
                    .id(rs.getInt("BOOK_ID"))
                    .title(rs.getString("TITLE"))
                    .description(rs.getString("DESCRIPTION"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book add(Book book) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("INSERT INTO BOOK (TITLE,DESCRIPTION) VALUES (?,?) RETURNING *");
            psmt.setString(1, book.getTitle());
            psmt.setString(2, book.getDescription());
            ResultSet rs = psmt.executeQuery();
            if(!rs.next())
                throw new EntityException("Failed");

            conn.close();
            return buildEntity(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Integer id, Book book) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("UPDATE BOOK SET TITLE = ?, DESCRIPTION = ? WHERE MOVIE_ID = ?");
            psmt.setString(1,book.getTitle());
            psmt.setString(2, book.getDescription());
            psmt.setInt(3,id);

            int nbRows = psmt.executeUpdate();

            conn.close();

            return nbRows == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
