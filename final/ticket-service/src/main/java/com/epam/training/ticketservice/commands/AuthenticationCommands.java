package com.epam.training.ticketservice.commands;

import org.springframework.shell.Availability;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellMethodAvailability;

@ShellComponent
public class AuthenticationCommands {

    static boolean isLogged = false;
    private final String adminUsername = "admin";

    @SuppressWarnings("unused")
    public static boolean isIsLogged() {
        return isLogged;
    }

    @SuppressWarnings("unused")
    public static void setIsLogged(boolean isLogged) {
        AuthenticationCommands.isLogged = isLogged;
    }

    @ShellMethod(key = "sign in privileged", value = "Login as the admin user")
    @ShellMethodAvailability("isLoggedOut")
    public String login(String username, String password) {
        String adminPassword = "admin";
        if (adminUsername.equals(username) && adminPassword.equals(password)) {
            isLogged = true;
            return null;
        }
        return "Login failed due to incorrect credentials";
    }

    @ShellMethod(key = "sign out", value = "Log out as the admin user.")
    @ShellMethodAvailability("isLoggedIn")
    public void logout() {
        isLogged = false;
    }

    @ShellMethod(key = "describe account", value = "Describe the account.")
    public String describeAccount() {
        if (isLogged) {
            return String.format("Signed in with privileged account '%s'", adminUsername);
        } else {
            return "You are not signed in";
        }
    }

    public Availability isLoggedIn() {
        if (isLogged) {
            return Availability.available();
        } else {
            return Availability.unavailable("you are not logged in.");
        }
    }

    public Availability isLoggedOut() {
        if (isLogged) {
            return Availability.unavailable("you are logged in.");
        } else {
            return Availability.available();
        }
    }
}