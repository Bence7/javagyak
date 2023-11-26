package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;

    public MovieServiceImpl(MovieRepository movieRepository) {
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveMovie(Movie movie) {
        movieRepository.save(movie);
    }

    @Override
    public void updateMovie(String name, String genre, Integer length) {
        if (movieRepository.findById(name).isPresent()) {
            Movie movie = movieRepository.findById(name).get();
            movie.setGenre(genre);
            movie.setLength(length);
            movieRepository.save(movie);
        }
    }

    @Override
    public void deleteMovie(String name) {
        movieRepository.deleteById(name);
    }

    @Override
    public StringBuilder listMovies() {
        StringBuilder movieList = new StringBuilder();
        movieRepository.findAll().forEach(movie -> movieList.append(movie.toString()).append("\n"));
        if (movieList.length() > 2) {
            movieList.delete(movieList.length() - 1, movieList.length());
            return movieList;
        } else {
            return new StringBuilder("There are no movies at the moment");
        }
    }

    @Override
    public Optional<Movie> findById(String movieName) {
        return movieRepository.findById(movieName);
    }
}
