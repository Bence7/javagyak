package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.entities.Screening;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.types.CustomDateTime;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;


@ShellComponent
@ShellCommandGroup(value = "Admin Commands")
public class ScreeningCommands {
    private final ScreeningService screeningService;
    private final MovieService movieService;
    private final RoomService roomService;

    public ScreeningCommands(ScreeningService screeningService, MovieService movieService, RoomService roomService) {
        this.screeningService = screeningService;
        this.movieService = movieService;
        this.roomService = roomService;
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "delete screening", value = "Delete screening.")
    public void deleteScreening(String movieName, String roomName, String customDate) {
        screeningService.deleteScreening(movieName, roomName, customDate);
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "create screening", value = "Create screening.")
    public String create(String movieName, String roomName, String dateTime) {
        Movie movie = movieService.findById(movieName).orElse(null);
        Room room = roomService.findById(roomName).orElse(null);

        if (movie == null || room == null) {
            return "Movie or room not found.";
        }

        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString(dateTime);


        LocalDateTime newScreeningStart = customDateTime.getDateTime();  //18:39
        LocalDateTime newScreeningEnd = newScreeningStart.plusMinutes(movie.getLength());  //18:39+120=20:39

        for (Screening screening : screeningService.findAll()) {
            LocalDateTime screeningEnd = screening.getDateTime()
                    .plusMinutes(screening.getMovie().getLength()); //11:00+450=18:30

            if (newScreeningEnd.plusMinutes(10).isBefore(screening.getDateTime())
                    || newScreeningStart.isAfter(screeningEnd.plusMinutes(9))) {
                screeningService.saveScreening(new Screening(movie, room, newScreeningStart));
                return null;
            } else {
                return (newScreeningStart.isBefore(screeningEnd.plusMinutes(10))
                        && newScreeningStart.isAfter(screeningEnd))
                        ? "This would start in the break period after another screening in this room" :
                        "There is an overlapping screening";
            }
        }
        screeningService.saveScreening(new Screening(movie, room, newScreeningStart));
        return null;
    }

    @ShellMethod(key = "list screenings", value = "List screenings.")
    public String listScreenings() {
        var list = screeningService.findAll();
        if (list.isEmpty()) {
            return "There are no screenings";
        }
        StringBuilder asd = new StringBuilder();
        for (var l : list) {
            asd.append(l.toString()).append("\n");
        }
        return asd.toString();
    }

    public Availability isLoggedIn() {
        if (AuthenticationCommands.isLogged) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in.");
        }
    }
}