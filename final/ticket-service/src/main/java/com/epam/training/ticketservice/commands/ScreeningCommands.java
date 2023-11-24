package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.entities.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.types.CustomDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

import java.time.LocalDateTime;

@ShellComponent
@ShellCommandGroup(value = "Admin Commands")
public class ScreeningCommands implements ScreeningService {

    private final ScreeningRepository screeningRepository;
    private final RoomRepository roomRepository;
    private final MovieRepository movieRepository;
    Logger logger = LoggerFactory.getLogger(ScreeningCommands.class);

    public ScreeningCommands(ScreeningRepository screeningRepository,
                             RoomRepository roomRepository, MovieRepository movieRepository) {
        this.screeningRepository = screeningRepository;
        this.roomRepository = roomRepository;
        this.movieRepository = movieRepository;
    }

    @Override
    public void saveScreening(Screening screening) {
        screeningRepository.save(screening);
    }

    @Override
    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "delete screening", value = "Delete screening.")
    public void deleteScreening(String movieName, String roomName, String customDate) {
        screeningRepository.findAll().forEach(screening -> {
            if (screening.getRoom().getName().equals(roomName)
                    && screening.getMovie().getName().equals(movieName)
                    && screening.getCustomDate().equals(customDate)) {
                logger.info(String.valueOf(screening));
                screeningRepository.delete(screening);
            }
        });
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "create screening", value = "Create screening.")
    public String create(String movieName, String roomName, String dateTime) {
        Movie movie = movieRepository.findById(movieName).orElse(null);
        Room room = roomRepository.findById(roomName).orElse(null);

        if (movie == null || room == null) {
            return "Movie or room not found.";
        }

        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString(dateTime);


        LocalDateTime newScreeningStart = customDateTime.getDateTime();  //18:39
        LocalDateTime newScreeningEnd = newScreeningStart.plusMinutes(movie.getLength());  //18:39+120=20:39

        for (Screening screening : screeningRepository.findAll()) {
            LocalDateTime screeningEnd = screening.getDateTime().plusMinutes(screening.getMovie().getLength()); //11:00+450=18:30

            if (newScreeningEnd.plusMinutes(10).isBefore(screening.getDateTime()) || newScreeningStart.isAfter(screeningEnd.plusMinutes(9))) {
                saveScreening(new Screening(movie, room, newScreeningStart));
                return null;
            }
            else {
                return (newScreeningStart.isBefore(screeningEnd.plusMinutes(10)) && newScreeningStart.isAfter(screeningEnd)) ?
                        "This would start in the break period after another screening in this room":
                        "There is an overlapping screening";
            }
        }
        saveScreening(new Screening(movie, room, newScreeningStart));
        return null;
    }

    @Override
    @ShellMethod(key = "list screenings", value = "List screenings.")
    public StringBuilder listScreenings() {
        StringBuilder screeningList = new StringBuilder();
        screeningRepository.findAll().forEach(movie -> screeningList.append(movie.toString()).append("\n"));
        if (screeningList.length() > 2) {
            screeningList.delete(screeningList.length() - 1, screeningList.length());
            return screeningList;
        } else {
            return new StringBuilder("There are no screenings");
        }
    }

    public Availability isLoggedIn() {
        if (AuthenticationCommands.isLogged) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in.");
        }
    }
}