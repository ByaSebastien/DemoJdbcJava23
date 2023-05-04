package org.example.services;

import org.example.exception.EntityNotFoundException;
import org.example.models.entities.Book;
import org.example.repositories.BookRepository;
import org.example.repositories.BookRepositoryImpl;

import java.util.List;

public class BookServiceImpl {

    private final BookRepository bookRepository;

    public BookServiceImpl(){

        this.bookRepository = new BookRepositoryImpl();
    }

    public Book getOne(Integer id){

        return bookRepository.getOne(id);
    }

    public List<Book> getMany(){

        return bookRepository.getMany();
    }

    public Book add(Book book){

        return bookRepository.add(book);
    }

    public boolean delete(Integer id){

        if(!bookRepository.delete(id))
            throw new EntityNotFoundException();

        return true;
    }
}














