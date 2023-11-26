package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RoomServiceImpl implements RoomService {

    private final RoomRepository roomRepository;
    public RoomServiceImpl(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    @Override
    public void saveRoom(Room room) {
        roomRepository.save(room);
    }

    @Override
    public void updateRoom(String name, Integer rows, Integer columns) {
        if (roomRepository.findById(name).isPresent()) {
            Room room = roomRepository.findById(name).get();
            room.setRows(rows);
            room.setColumns(columns);
            roomRepository.save(room);
        }
    }

    @Override
    public void deleteRoom(String name) {
        roomRepository.deleteById(name);
    }

    @Override
    public StringBuilder listRooms() {
        StringBuilder roomList = new StringBuilder();
        roomRepository.findAll().forEach(movie -> roomList.append(movie.toString()).append("\n"));
        if (roomList.length() > 2) {
            roomList.delete(roomList.length() - 1, roomList.length());
            return roomList;
        } else {
            return new StringBuilder("There are no rooms at the moment");
        }
    }

    @Override
    public Optional<Room> findById(String roomName) {
        return roomRepository.findById(roomName);
    }
}
