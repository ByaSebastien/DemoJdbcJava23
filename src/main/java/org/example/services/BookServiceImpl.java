package org.example.services;

import org.example.exception.EntityNotFoundException;
import org.example.models.entities.Book;
import org.example.repositories.AuthorRepository;
import org.example.repositories.impl.AuthorRepositoryImpl;
import org.example.repositories.BookRepository;
import org.example.repositories.impl.BookRepositoryImpl;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.List;

public class BookServiceImpl {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public BookServiceImpl() {

        this.bookRepository = new BookRepositoryImpl();
        this.authorRepository = new AuthorRepositoryImpl();
    }

    public Book getOne(Integer id) {

        return bookRepository.getOne(id);
    }

    public List<Book> getMany() {

        return bookRepository.getMany();
    }

    public Book add(Book book) {

        try {
            Connection conn = DriverManager.getConnection("jdbc:postgresql://localhost:5433/DemoJdbc", "postgres", "postgres");
            conn.setAutoCommit(false);

            if (book.getAuthor() != null) {

                int id = authorRepository.add(book.getAuthor(),conn).getId();
                book.setAuthorId(id);
            }
            if (book.getAuthorId() == null)
                throw new EntityNotFoundException();

            Book resultBook = bookRepository.add(book,conn);
            conn.commit();
            return resultBook;
        } catch (SQLException e) {

            throw new RuntimeException(e);
        }
    }

    public boolean delete(Integer id) {

        if (!bookRepository.delete(id))
            throw new EntityNotFoundException();

        return true;
    }

    public boolean update(Integer id, Book book) {

        if (!bookRepository.update(id, book))
            throw new EntityNotFoundException();

        return true;
    }
}














