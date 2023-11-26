package com.epam.training.ticketservice.impl;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.impl.MovieServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

public class MovieServiceImplTest {

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private MovieServiceImpl movieService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveMovie_ShouldCallRepositorySaveMethod() {
        // Arrange
        Movie movie = new Movie("MovieName", "Action", 120);

        // Act
        movieService.saveMovie(movie);

        // Assert
        verify(movieRepository, times(1)).save(movie);
    }

    @Test
    void updateMovie_WhenMovieExists_ShouldUpdateMovie() {
        // Arrange
        String movieName = "ExistingMovie";
        String newGenre = "Comedy";
        Integer newLength = 90;
        Movie existingMovie = new Movie(movieName, "Drama", 120);

        when(movieRepository.findById(movieName)).thenReturn(Optional.of(existingMovie));

        // Act
        movieService.updateMovie(movieName, newGenre, newLength);

        // Assert
        verify(movieRepository, times(1)).save(existingMovie);
        assertEquals(newGenre, existingMovie.getGenre());
        assertEquals(newLength, existingMovie.getLength());
    }

    @Test
    void updateMovie_WhenMovieDoesNotExist_ShouldNotUpdateMovie() {
        // Arrange
        String movieName = "NonExistingMovie";

        when(movieRepository.findById(movieName)).thenReturn(Optional.empty());

        // Act
        movieService.updateMovie(movieName, "Comedy", 90);

        // Assert
        verify(movieRepository, never()).save(any());
    }

    @Test
    void deleteMovie_ShouldCallRepositoryDeleteByIdMethod() {
        // Arrange
        String movieName = "MovieToDelete";

        // Act
        movieService.deleteMovie(movieName);

        // Assert
        verify(movieRepository, times(1)).deleteById(movieName);
    }

    @Test
    void listMovies_WhenMoviesExist_ShouldReturnMovieList() {
        // Arrange
        List<Movie> movieList = new ArrayList<>();
        movieList.add(new Movie("Movie1", "Action", 120));
        movieList.add(new Movie("Movie2", "Comedy", 90));

        when(movieRepository.findAll()).thenReturn(movieList);

        // Act
        StringBuilder result = movieService.listMovies();

        // Assert
        assertTrue(result.toString().contains("Movie1"));
        assertTrue(result.toString().contains("Movie2"));
        assertTrue(result.toString().contains("Action"));
        assertTrue(result.toString().contains("Comedy"));
        assertTrue(result.toString().contains("120"));
        assertTrue(result.toString().contains("90"));
    }

    @Test
    void listMovies_WhenNoMoviesExist_ShouldReturnErrorMessage() {
        // Arrange
        when(movieRepository.findAll()).thenReturn(new ArrayList<>());

        // Act
        StringBuilder result = movieService.listMovies();

        // Assert
        assertEquals("There are no movies at the moment", result.toString());
    }

    @Test
    void findById_ShouldReturnOptionalMovie() {
        // Arrange
        String movieName = "ExistingMovie";
        Movie existingMovie = new Movie(movieName, "Drama", 120);

        when(movieRepository.findById(movieName)).thenReturn(Optional.of(existingMovie));

        // Act
        Optional<Movie> result = movieService.findById(movieName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(existingMovie, result.get());
    }

    @Test
    void findById_WhenMovieDoesNotExist_ShouldReturnEmptyOptional() {
        // Arrange
        String movieName = "NonExistingMovie";

        when(movieRepository.findById(movieName)).thenReturn(Optional.empty());

        // Act
        Optional<Movie> result = movieService.findById(movieName);

        // Assert
        assertTrue(result.isEmpty());
    }
}
