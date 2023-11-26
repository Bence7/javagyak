package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.entities.Screening;
import com.epam.training.ticketservice.service.MovieService;
import com.epam.training.ticketservice.service.RoomService;
import com.epam.training.ticketservice.service.ScreeningService;
import com.epam.training.ticketservice.types.CustomDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.shell.Availability;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class ScreeningCommandsTest {

    @Mock
    private ScreeningService screeningService;

    @Mock
    private MovieService movieService;

    @Mock
    private RoomService roomService;

    private ScreeningCommands screeningCommands;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        screeningCommands = new ScreeningCommands(screeningService, movieService, roomService);
    }

    @Test
    void deleteScreening_shouldCallDeleteScreening() {
        // Arrange
        String movieName = "Movie1";
        String roomName = "Room1";
        String customDate = "2023-11-26 15:30";

        // Act
        screeningCommands.deleteScreening(movieName, roomName, customDate);

        // Assert
        verify(screeningService, times(1)).deleteScreening(movieName, roomName, customDate);
    }

    @Test
    void create_shouldReturnErrorMessageWhenMovieOrRoomNotFound() {
        // Arrange
        String movieName = "NonExistentMovie";
        String roomName = "NonExistentRoom";
        String dateTime = "2023-11-26 15:30";
        when(movieService.findById(movieName)).thenReturn(Optional.empty());
        when(roomService.findById(roomName)).thenReturn(Optional.empty());

        // Act
        String result = screeningCommands.create(movieName, roomName, dateTime);

        // Assert
        assertEquals("Movie or room not found.", result);
        verify(screeningService, never()).saveScreening(any());
    }

    @Test
    void create_shouldSaveScreeningWhenNoOverlap() {
        // Arrange
        String movieName = "Movie1";
        String roomName = "Room1";
        String dateTime = "2023-11-26 15:30";
        Movie movie = new Movie("Movie1", "Genre", 120);
        Room room = new Room("Room1", 5, 8);
        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString(dateTime);

        when(movieService.findById(movieName)).thenReturn(Optional.of(movie));
        when(roomService.findById(roomName)).thenReturn(Optional.of(room));
        when(screeningService.findAll()).thenReturn(java.util.List.of());

        // Act
        String result = screeningCommands.create(movieName, roomName, dateTime);

        // Assert
        assertNull(result);
        verify(screeningService, times(1)).saveScreening(any());
    }
}
