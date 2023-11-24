package com.epam.training.ticketservice.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Movie {
    @Id
    private String name;
    @Column(nullable = false)
    private String genre;
    @Column(nullable = false)
    private Integer length;

    public Movie() {
    }

    public Movie(String name, String genre, Integer length) {
        this.name = name;
        this.genre = genre;
        this.length = length;
    }

    public String getName() {
        return name;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return String.format("%s (%s, %d minutes)", name, genre, length);
    }
}