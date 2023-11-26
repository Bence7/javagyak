package com.epam.training.ticketservice.impl;

import com.epam.training.ticketservice.commands.MovieCommands;
import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieCommandsTest {

    @Mock
    private MovieService movieService;

    @Mock
    private Logger logger;

    @InjectMocks
    private MovieCommands movieCommands;

    private MovieRepository movieRepository;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListMoviesWhenMoviesExist() {
        StringBuilder result = mock(StringBuilder.class);
        movieService.saveMovie(new Movie("Movie1", "Action", 120));
        assertEquals("Movie1 (Action, 120 minutes)", result.toString());
    }

    @Test
    public void testListMoviesWhenNoMoviesExist() {
        StringBuilder result = movieCommands.listMovies();
        assertEquals("There are no movies at the moment", result.toString().trim());
    }

    @Test
    public void testCreateMovie() {
        movieCommands.create("Movie1", "Action", 120);
        verify(movieService, times(1)).saveMovie(any(Movie.class));
    }

    @Test
    public void testUpdateMovieWhenMovieExists() {
        Movie existingMovie = new Movie("Movie1", "Action", 120);
        when(movieService.findById("Movie1")).thenReturn(Optional.of(existingMovie));
        movieCommands.update("Movie1", "Comedy", 150);
        verify(movieService, times(1)).saveMovie(existingMovie);
    }

    @Test
    public void testUpdateMovieWhenMovieDoesNotExist() {
        when(movieService.findById("NonExistentMovie")).thenReturn(Optional.empty());
        movieCommands.update("NonExistentMovie", "Comedy", 150);
        verify(movieService, never()).saveMovie(any(Movie.class));
        verify(logger, never()).info(anyString());
    }

    @Test
    public void testDeleteMovie() {
        movieCommands.delete("Movie1");
        verify(movieService, times(1)).deleteMovie("Movie1");
    }
}