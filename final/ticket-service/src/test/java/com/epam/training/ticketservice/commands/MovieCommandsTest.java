package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class MovieCommandsTest {

    @Mock
    private MovieRepository movieRepository;

    @Mock
    private Logger logger;

    @InjectMocks
    private MovieCommands movieCommands;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListMoviesWhenMoviesExist() {
        when(movieRepository.findAll()).thenReturn(List.of(new Movie("Movie1", "Action", 120)));
        StringBuilder result = movieCommands.listMovies();
        assertEquals("Movie1 (Action, 120 minutes)", result.toString());
    }

    @Test
    public void testListMoviesWhenNoMoviesExist() {
        when(movieRepository.findAll()).thenReturn(Collections.emptyList());
        StringBuilder result = movieCommands.listMovies();
        assertEquals("There are no movies at the moment", result.toString().trim());
    }

    @Test
    public void testCreateMovie() {
        movieCommands.create("Movie1", "Action", 120);
        verify(movieRepository, times(1)).save(any(Movie.class));
    }

    @Test
    public void testUpdateMovieWhenMovieExists() {
        Movie existingMovie = new Movie("Movie1", "Action", 120);
        when(movieRepository.findById("Movie1")).thenReturn(Optional.of(existingMovie));
        movieCommands.update("Movie1", "Comedy", 150);
        verify(movieRepository, times(1)).save(existingMovie);
    }

    @Test
    public void testUpdateMovieWhenMovieDoesNotExist() {
        when(movieRepository.findById("NonExistentMovie")).thenReturn(Optional.empty());
        movieCommands.update("NonExistentMovie", "Comedy", 150);
        verify(movieRepository, never()).save(any(Movie.class));
        verify(logger, never()).info(anyString());
    }

    @Test
    public void testDeleteMovie() {
        movieCommands.delete("Movie1");
        verify(movieRepository, times(1)).deleteById("Movie1");
    }
}
