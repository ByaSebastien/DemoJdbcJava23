package org.example.repositories.impl;

import org.example.exception.EntityException;
import org.example.models.entities.Book;
import org.example.repositories.BookRepository;

import java.sql.*;
import java.util.List;

public class BookRepositoryImpl extends BaseRepositoryImpl<Book> implements BookRepository {


    public BookRepositoryImpl() {
        super("BOOK", "BOOK_ID");
    }

    @Override
    protected Book buildEntity(ResultSet rs) {

        try {
            return Book.builder()
                    .id(rs.getInt("BOOK_ID"))
                    .title(rs.getString("TITLE"))
                    .description(rs.getString("DESCRIPTION"))
                    .authorId(rs.getInt("AUTHOR_ID"))
                    .build();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book add(Book book, Connection conn) {

        try {
            PreparedStatement psmt = conn.prepareStatement("INSERT INTO BOOK (TITLE,DESCRIPTION,AUTHOR_ID) VALUES (?,?,?) RETURNING *");
            psmt.setString(1, book.getTitle());
            psmt.setString(2, book.getDescription());
            psmt.setInt(3, book.getAuthorId());
            ResultSet rs = psmt.executeQuery();
            if (!rs.next())
                throw new EntityException("Failed");

            return buildEntity(rs);

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Book add(Book book) {

        return add(book, openConnection());
    }

    public void add(List<Book> books) {
        try {
            Connection conn = openConnection();
            conn.setAutoCommit(false);
            PreparedStatement psmt = conn.prepareStatement("INSERT INTO BOOK (TITLE,DESCRIPTION,AUTHOR_ID) VALUES (?,?,?)");
            for (Book book : books) {

                psmt.setString(1, book.getTitle());
                psmt.setString(2, book.getDescription());
                psmt.setInt(3, book.getAuthorId());
                psmt.addBatch();
            }
            int[] results = psmt.executeBatch();
            conn.rollback();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public boolean update(Integer id, Book book) {

        try {
            Connection conn = openConnection();
            PreparedStatement psmt = conn.prepareStatement("UPDATE BOOK SET TITLE = ?, DESCRIPTION = ?, AUTHOR_ID = ? WHERE BOOK_ID = ?");
            psmt.setString(1, book.getTitle());
            psmt.setString(2, book.getDescription());
            psmt.setInt(3, book.getAuthorId());
            psmt.setInt(4, id);

            int nbRows = psmt.executeUpdate();

            conn.close();

            return nbRows == 1;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
