package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entities.Room;

import java.util.Optional;


public interface RoomService {
    void saveRoom(Room room);

    void updateRoom(String name, Integer rows, Integer columns);

    void deleteRoom(String name);

    StringBuilder listRooms();

    Optional<Room> findById(String roomName);
}
