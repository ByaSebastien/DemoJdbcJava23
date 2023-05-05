package org.example.services;

import org.example.exception.EntityNotFoundException;
import org.example.models.entities.Book;
import org.example.models.entities.Movie;
import org.example.repositories.BookRepository;
import org.example.repositories.BookRepositoryImpl;
import org.example.repositories.MovieRepository;
import org.example.repositories.MovieRepositoryImpl;

import java.util.List;

public class MovieServiceImpl {

    private final MovieRepository movieRepository;

    public MovieServiceImpl(){

        this.movieRepository = new MovieRepositoryImpl();
    }

    public Movie getOne(Integer id){

        return movieRepository.getOne(id);
    }

    public List<Movie> getMany(){

        return movieRepository.getMany();
    }

    public Movie add(Movie movie){

        return movieRepository.add(movie);
    }

    public boolean delete(Integer id){

        if(!movieRepository.delete(id))
            throw new EntityNotFoundException();

        return true;
    }

    public boolean update(Integer id, Movie movie){

        if(!movieRepository.update(id,movie))
            throw new EntityNotFoundException();

        return true;
    }
}
