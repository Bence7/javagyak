package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.slf4j.Logger;
import org.springframework.shell.Availability;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
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
    void isLoggedIn_whenLogged_shouldReturnAvailable() {
        // Arrange
        AuthenticationCommands.isLogged = true;

        // Act
        Availability availability = movieCommands.isLoggedIn();

        // Assert
        assertEquals(true, availability.isAvailable());
    }

    @Test
    void isLoggedIn_whenNotLogged_shouldReturnUnavailable() {
        // Arrange
        AuthenticationCommands.isLogged = false;

        // Act
        Availability availability = movieCommands.isLoggedIn();

        // Assert
        assertEquals(false, availability.isAvailable());
    }
}