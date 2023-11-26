package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entities.Movie;

import java.util.Optional;


public interface MovieService {
    void saveMovie(Movie movie);

    void updateMovie(String name, String genre, Integer length);

    void deleteMovie(String name);

    StringBuilder listMovies();

    Optional<Movie> findById(String movieName);
}