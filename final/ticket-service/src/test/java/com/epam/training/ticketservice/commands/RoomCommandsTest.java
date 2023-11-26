package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.service.RoomService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.shell.Availability;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

public class RoomCommandsTest {

    @Mock
    private RoomService roomService;

    private RoomCommands roomCommands;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        roomCommands = new RoomCommands(roomService);
    }

    @Test
    void listRooms_shouldReturnStringBuilder() {
        // Arrange
        StringBuilder expectedStringBuilder = new StringBuilder();
        when(roomService.listRooms()).thenReturn(expectedStringBuilder);

        // Act
        StringBuilder actualStringBuilder = roomCommands.listRooms();

        // Assert
        assertEquals(expectedStringBuilder, actualStringBuilder);
    }

    @Test
    void delete_shouldCallDeleteRoom() {
        // Arrange
        String name = "Room1";
        roomCommands.create(name, 1,1);

        // Act
        roomCommands.delete(name);

        // Assert
       assertEquals( null, roomCommands.listRooms());
    }
}
