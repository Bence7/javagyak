package com.epam.training.ticketservice.commands;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.shell.Availability;

import static org.junit.jupiter.api.Assertions.*;

public class AuthenticationCommandsTest {

    private AuthenticationCommands authenticationCommands;

    @BeforeEach
    public void setUp() {
        authenticationCommands = new AuthenticationCommands();
    }

    @Test
    public void testLoginWithCorrectCredentials() {
        String result = authenticationCommands.login("admin", "admin");
        assertNull(result);
        assertTrue(AuthenticationCommands.isLogged);
    }

    @Test
    public void testLoginWithIncorrectCredentials() {
        String result = authenticationCommands.login("admin", "wrongpassword");
        assertEquals("Login failed due to incorrect credentials", result);
        assertFalse(AuthenticationCommands.isLogged);
    }

    @Test
    public void testLogout() {
        AuthenticationCommands.isLogged = true;
        authenticationCommands.logout();
        assertFalse(AuthenticationCommands.isLogged);
    }

    @Test
    public void testDescribeAccountWhenLoggedIn() {
        AuthenticationCommands.isLogged = true;
        String result = authenticationCommands.describeAccount();
        assertEquals("Signed in with privileged account 'admin'", result);
    }

    @Test
    public void testDescribeAccountWhenLoggedOut() {
        AuthenticationCommands.isLogged = false;
        String result = authenticationCommands.describeAccount();
        assertEquals("You are not signed in", result);
    }

    @Test
    public void testIsLoggedIn() {
        AuthenticationCommands.isLogged = true;
        Availability availability = authenticationCommands.isLoggedIn();
        assertTrue(availability.isAvailable());
    }

    @Test
    public void testIsLoggedOut() {
        AuthenticationCommands.isLogged = false;
        Availability availability = authenticationCommands.isLoggedOut();
        assertTrue(availability.isAvailable());
    }
}