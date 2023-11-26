package com.epam.training.ticketservice.entities;

import com.epam.training.ticketservice.types.CustomDateTime;

import javax.persistence.Id;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.ManyToOne;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Entity
public class Screening {

    @SuppressWarnings("unused")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    private Room room;

    @ManyToOne
    private Movie movie;

    @Column(nullable = false)
    private LocalDateTime dateTime;

    public Screening() {
    }

    public Screening(Movie movie, Room room, LocalDateTime localDateTime) {
        this.movie = movie;
        this.room = room;
        this.dateTime = localDateTime;
    }

    public Room getRoom() {
        return room;
    }

    public Movie getMovie() {
        return movie;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getCustomDate() {
        CustomDateTime customDateTime = new CustomDateTime();
        return customDateTime.formatLocalDateTimeToString(this.dateTime);
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %d minutes), screened in room %s, at %s",
                movie.getName(), movie.getGenre(), movie.getLength(), room.getName(), getCustomDate());
    }

}
