package com.epam.training.ticketservice.impl;

import com.epam.training.ticketservice.entities.Movie;
import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.entities.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.impl.ScreeningServiceImpl;
import com.epam.training.ticketservice.types.CustomDateTime;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class ScreeningServiceImplTest {

    @Mock
    private ScreeningRepository screeningRepository;

    private ScreeningServiceImpl screeningService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        screeningService = new ScreeningServiceImpl(screeningRepository);
    }

    @Test
    void findAll_shouldReturnListOfScreenings() {
        // Arrange
        Screening screening1 = new Screening();
        Screening screening2 = new Screening();
        when(screeningRepository.findAll()).thenReturn(List.of(screening1, screening2));

        // Act
        List<Screening> result = screeningService.findAll();

        // Assert
        assertEquals(2, result.size());
        assertTrue(result.contains(screening1));
        assertTrue(result.contains(screening2));
    }

    @Test
    void deleteScreening_shouldCallDelete() {
        // Arrange
        String movieName = "Movie1";
        String roomName = "Room1";
        String customDate = "2023-11-26 15:30";
        CustomDateTime customDateTime = new CustomDateTime();
        customDateTime.setDateTimeFromString(customDate);
        Screening screening = new Screening(new Movie(movieName, "asd",12), new Room(roomName, 1,1),customDateTime.getDateTime());
        when(screeningRepository.findAll()).thenReturn(List.of(screening));

        // Act
        screeningService.deleteScreening(movieName, roomName, customDate);

        // Assert
        verify(screeningRepository).delete(screening);
    }
}
