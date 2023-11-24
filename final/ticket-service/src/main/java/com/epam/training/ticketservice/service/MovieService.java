package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entities.Movie;
import org.springframework.stereotype.Service;

@Service
public interface MovieService {
    void saveMovie(Movie movie);

    void updateMovie(String name, String genre, Integer length);

    void deleteMovie(String name);
    StringBuilder listMovies();
}