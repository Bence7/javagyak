package com.epam.training.ticketservice.commands;

import com.epam.training.ticketservice.entities.Room;
import com.epam.training.ticketservice.service.RoomService;
import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellCommandGroup;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
@ShellCommandGroup(value = "Admin Commands")
public class RoomCommands {
    private final RoomService roomService;

    public RoomCommands(RoomService roomService) {
        this.roomService = roomService;
    }

    @ShellMethod(key = "list rooms", value = "Delete room by name.")
    public StringBuilder listRooms() {
        return roomService.listRooms();
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "create room", value = "Create room with its name, rows and columns.")
    public void create(String name, Integer rows, Integer columns) {
        roomService.saveRoom(new Room(name, rows, columns));
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "update room", value = "Create room with its name, rows and columns.")
    public void update(String name, Integer rows, Integer columns) {
        roomService.updateRoom(name, rows, columns);
    }

    @ShellMethodAvailability("isLoggedIn")
    @ShellMethod(key = "delete room", value = "Delete room by name.")
    public void delete(String name) {
        roomService.deleteRoom(name);
    }

    public Availability isLoggedIn() {
        if (AuthenticationCommands.isLogged) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in.");
        }
    }
}
