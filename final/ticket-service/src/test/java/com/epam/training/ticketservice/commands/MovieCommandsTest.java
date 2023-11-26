package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;

import java.util.Optional;

import static org.mockito.Mockito.*;

public class MovieCommandsTest {

    @Mock
    private MovieService movieService;
    @Mock
    private Logger logger;
    @Mock
    private MovieService mockMovieService;
    @InjectMocks
    private MovieCommands movieCommands;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        movieCommands = new MovieCommands(mockMovieService);
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
        verify(movieService, times(0)).deleteMovie("Movie1");
    }
}