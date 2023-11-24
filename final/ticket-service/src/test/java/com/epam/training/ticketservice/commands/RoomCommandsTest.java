package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.shell.Availability;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class RoomCommandsTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomCommands roomCommands;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testListRoomsWhenRoomsExist() {
        when(roomRepository.findAll()).thenReturn(List.of(new Room("Room1", 10, 10)));
        StringBuilder result = roomCommands.listRooms();
        assertEquals("Room Room1 with 100 seats, 10 rows and 10 columns", result.toString().trim());
    }

    @Test
    public void testListRoomsWhenNoRoomsExist() {
        when(roomRepository.findAll()).thenReturn(Collections.emptyList());
        StringBuilder result = roomCommands.listRooms();
        assertEquals("There are no rooms at the moment", result.toString().trim());
    }

    @Test
    public void testCreateRoom() {
        roomCommands.create("Room1", 10, 10);
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    public void testUpdateRoomWhenRoomExists() {
        Room existingRoom = new Room("Room1", 10, 10);
        when(roomRepository.findById("Room1")).thenReturn(Optional.of(existingRoom));
        roomCommands.update("Room1", 15, 15);
        verify(roomRepository, times(1)).save(existingRoom);
    }

    @Test
    public void testUpdateRoomWhenRoomDoesNotExist() {
        when(roomRepository.findById("NonExistentRoom")).thenReturn(Optional.empty());
        roomCommands.update("NonExistentRoom", 15, 15);
        verify(roomRepository, never()).save(any(Room.class));
    }

    @Test
    public void testDeleteRoom() {
        roomCommands.delete("Room1");
        verify(roomRepository, times(1)).deleteById("Room1");
    }

    @Test
    public void testIsLoggedIn() {
        AuthenticationCommands.isLogged = true;
        Availability availability = roomCommands.isLoggedIn();
        assertTrue(availability.isAvailable());
    }

    @Test
    public void testIsNotLoggedIn() {
        AuthenticationCommands.isLogged = false;
        Availability availability = roomCommands.isLoggedIn();
        assertFalse(availability.isAvailable());
    }
}
