package com.epam.training.ticketservice.impl;

import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.impl.RoomServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void saveRoom_ShouldCallRepositorySaveMethod() {
        // Arrange
        Room room = new Room("RoomName", 5, 6);

        // Act
        roomService.saveRoom(room);

        // Assert
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void updateRoom_WhenRoomExists_ShouldUpdateRoom() {
        // Arrange
        String roomName = "ExistingRoom";
        Integer newRows = 8;
        Integer newColumns = 10;
        Room existingRoom = new Room(roomName, 5, 6);

        when(roomRepository.findById(roomName)).thenReturn(Optional.of(existingRoom));

        // Act
        roomService.updateRoom(roomName, newRows, newColumns);

        // Assert
        verify(roomRepository, times(1)).save(existingRoom);
        assertEquals(newColumns, existingRoom.getColumns());
    }

    @Test
    void updateRoom_WhenRoomDoesNotExist_ShouldNotUpdateRoom() {
        // Arrange
        String roomName = "NonExistingRoom";

        when(roomRepository.findById(roomName)).thenReturn(Optional.empty());

        // Act
        roomService.updateRoom(roomName, 8, 10);

        // Assert
        verify(roomRepository, never()).save(any());
    }

    @Test
    void deleteRoom_ShouldCallRepositoryDeleteByIdMethod() {
        // Arrange
        String roomName = "RoomToDelete";

        // Act
        roomService.deleteRoom(roomName);

        // Assert
        verify(roomRepository, times(1)).deleteById(roomName);
    }

    @Test
    void listRooms_WhenNoRoomsExist_ShouldReturnErrorMessage() {
        // Act
        StringBuilder result = roomService.listRooms();

        // Assert
        assertEquals("There are no rooms at the moment", result.toString());
    }

    @Test
    void findById_ShouldReturnOptionalRoom() {
        // Arrange
        String roomName = "ExistingRoom";
        Room existingRoom = new Room(roomName, 5, 6);

        when(roomRepository.findById(roomName)).thenReturn(Optional.of(existingRoom));

        // Act
        Optional<Room> result = roomService.findById(roomName);

        // Assert
        assertTrue(result.isPresent());
        assertEquals(existingRoom, result.get());
    }

    @Test
    void findById_WhenRoomDoesNotExist_ShouldReturnEmptyOptional() {
        // Arrange
        String roomName = "NonExistingRoom";

        when(roomRepository.findById(roomName)).thenReturn(Optional.empty());

        // Act
        Optional<Room> result = roomService.findById(roomName);

        // Assert
        assertTrue(result.isEmpty());
    }
}
