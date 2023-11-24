package com.epam.training.ticketservice.types;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CustomDateTime {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private LocalDateTime dateTime;

    public void setDateTimeFromString(String dateTimeString) {
        this.dateTime = LocalDateTime.parse(dateTimeString, formatter);
    }

    // Getter for LocalDateTime object
    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String formatLocalDateTimeToString(LocalDateTime dateTime) {
        return dateTime.format(formatter);
    }
}