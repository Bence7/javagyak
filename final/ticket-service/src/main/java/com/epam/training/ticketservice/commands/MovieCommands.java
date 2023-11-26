package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.service.MovieService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup(value = "Admin Commands")
public class MovieCommands {
    private final MovieService movieService;

    public MovieCommands(MovieService movieService) {
        this.movieService = movieService;
    }

    @ShellMethod(key = "list movies", value = "List movies.")
    public StringBuilder listMovies() {
        return movieService.listMovies();
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "create movie", value = "Create movie with its name, genre and length.")
    public void create(String name, String genre, Integer length) {
        movieService.saveMovie(new Movie(name, genre, length));
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "update movie", value = "Create movie with its name, genre and length.")
    public void update(String name, String genre, Integer length) {

        movieService.updateMovie(name, genre, length);
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "delete movie", value = "Delete movie by name.")
    public void delete(String name) {

        movieService.deleteMovie(name);
    }

    public Availability isLoggedIn() {
        if (AuthenticationCommands.isLogged) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in.");
        }
    }
}
