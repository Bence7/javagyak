package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entities.Room;
import org.springframework.stereotype.Service;

@Service
public interface RoomService {

    void saveRoom(Room room);

    void updateRoom(String name, Integer rows, Integer columns);


    void deleteRoom(String name);


    StringBuilder listRooms();
}
