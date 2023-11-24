package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.service.MovieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup(value = "Admin Commands")
public class MovieCommands implements MovieService {
    private final MovieRepository movieRepository;
    Logger logger = LoggerFactory.getLogger(MovieCommands.class);

    public MovieCommands(MovieRepository movieRepository) {
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
            logger.info(movie.toString());
        }
    }

    @Override
    @ShellMethod(key = "list movies", value = "List movies.")
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
    public void deleteMovie(String name) {
        movieRepository.deleteById(name);
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "create movie", value = "Create movie with its name, genre and length.")
    public void create(String name, String genre, Integer length) {
        saveMovie(new Movie(name, genre, length));
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "update movie", value = "Create movie with its name, genre and length.")
    public void update(String name, String genre, Integer length) {
        updateMovie(name, genre, length);
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "delete movie", value = "Delete movie by name.")
    public void delete(String name) {
        deleteMovie(name);
    }

    public Availability isLoggedIn() {
        if (AuthenticationCommands.isLogged) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in.");
        }
    }
}
