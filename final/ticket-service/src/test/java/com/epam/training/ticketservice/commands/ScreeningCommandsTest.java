package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.entities.Screening;
import com.epam.training.ticketservice.repository.MovieRepository;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.types.CustomDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.shell.Availability;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ScreeningCommandsTest {

    @Mock
    private ScreeningRepository screeningRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private MovieRepository movieRepository;

    @InjectMocks
    private ScreeningCommands screeningCommands;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateScreening() {
        Movie movie = new Movie("Movie1", "Action", 120);
        Room room = new Room("Room1", 10, 10);
        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString("2023-10-10 14:00");

        when(movieRepository.findById("Movie1")).thenReturn(Optional.of(movie));
        when(roomRepository.findById("Room1")).thenReturn(Optional.of(room));
        when(screeningRepository.findAll()).thenReturn(Collections.emptyList());

        String result = screeningCommands.create("Movie1", "Room1", "2023-10-10 14:00");
        verify(screeningRepository, times(1)).save(any(Screening.class));

        assertNull(result);
    }

    @Test
    public void testCreateScreeningWithOverlap() {
        Movie movie = new Movie("Movie1", "Action", 120);
        Room room = new Room("Room1", 10, 10);
        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString("2023-10-10 14:00");
        Screening existingScreening = new Screening(movie, room, customDateTime.getDateTime());
        when(movieRepository.findById("Movie1")).thenReturn(Optional.of(movie));
        when(roomRepository.findById("Room1")).thenReturn(Optional.of(room));
        when(screeningRepository.findAll()).thenReturn(List.of(existingScreening));

        String result = screeningCommands.create("Movie1", "Room1", "2023-10-10 14:00");
        verify(screeningRepository, never()).save(any(Screening.class));
        assertEquals("There is an overlapping screening", result);
    }
/*
    @Test
    public void testCreateScreeningInBreakPeriod() {
        Movie movie = new Movie("Movie1", "Action", 120);
        Room room = new Room("Room1", 10, 10);
        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString("2023-10-10 14:00");
        Screening existingScreening = new Screening(movie, room, customDateTime.getDateTime());

        when(movieRepository.findById("Movie1")).thenReturn(Optional.of(movie));
        when(roomRepository.findById("Room1")).thenReturn(Optional.of(room));
        when(screeningRepository.findAll()).thenReturn(List.of(existingScreening));

        String result = screeningCommands.create("Movie1", "Room1", "2023-10-10 16:05");
        verify(screeningRepository, never()).save(any(Screening.class));
        assertEquals("This would start in the break period after another screening in this room", result);
    }
*/
    @Test
    public void testListScreeningsWhenScreeningsExist() {
        Screening screening = new Screening(new Movie("Movie1", "Action", 120),
                new Room("Room1", 10, 10), LocalDateTime.now());
        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.formatLocalDateTimeToString(LocalDateTime.now());
        when(screeningRepository.findAll()).thenReturn(List.of(screening));
        StringBuilder result = screeningCommands.listScreenings();
        assertEquals("Movie1 (Action, 120 minutes), screened in room Room1, at " + customDateTime.formatLocalDateTimeToString(LocalDateTime.now()), result.toString().trim());
    }

    @Test
    public void testListScreeningsWhenNoScreeningsExist() {
        when(screeningRepository.findAll()).thenReturn(Collections.emptyList());
        StringBuilder result = screeningCommands.listScreenings();
        assertEquals("There are no screenings", result.toString().trim());
    }

    @Test
    public void testDeleteScreening() {
        Movie movie = new Movie("Movie1", "Action", 120);
        Room room = new Room("Room1", 10, 10);
        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString("2023-10-10 14:00");
        Screening existingScreening = new Screening(movie, room, customDateTime.getDateTime());
        when(movieRepository.findById("Movie1")).thenReturn(Optional.of(movie));
        when(roomRepository.findById("Room1")).thenReturn(Optional.of(room));
        when(screeningRepository.findAll()).thenReturn(List.of(existingScreening));

        screeningCommands.deleteScreening("Movie1", "Room1", "2023-10-10 14:00");
        verify(screeningRepository, times(1)).delete(existingScreening);
    }

    @Test
    public void testIsLoggedIn() {
        AuthenticationCommands.isLogged = true;
        Availability availability = screeningCommands.isLoggedIn();
        assertTrue(availability.isAvailable());
    }

    @Test
    public void testIsNotLoggedIn() {
        AuthenticationCommands.isLogged = false;
        Availability availability = screeningCommands.isLoggedIn();
        assertFalse(availability.isAvailable());
    }
}
