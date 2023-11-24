package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.repository.RoomRepository;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup(value = "Admin Commands")
public class RoomCommands implements RoomService {


    private final RoomRepository roomRepository;

    public RoomCommands(RoomRepository roomRepository) {
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
    @ShellMethod(key = "list rooms", value = "Delete room by name.")
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

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "create room", value = "Create room with its name, rows and columns.")
    public void create(String name, Integer rows, Integer columns) {
        saveRoom(new Room(name, rows, columns));
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "update room", value = "Create room with its name, rows and columns.")
    public void update(String name, Integer rows, Integer columns) {
        updateRoom(name, rows, columns);
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "delete room", value = "Delete room by name.")
    public void delete(String name) {
        deleteRoom(name);
    }

    public Availability isLoggedIn() {
        if (AuthenticationCommands.isLogged) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in.");
        }
    }
}
