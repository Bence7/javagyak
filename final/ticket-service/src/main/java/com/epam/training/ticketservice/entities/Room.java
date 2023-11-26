package com.epam.training.ticketservice.entities;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class Room {
    @Id
    private String name;
    private Integer rows;
    private Integer columns;

    public Room() {
    }

    public Room(String name, Integer rows, Integer columns) {
        this.name = name;
        this.rows = rows;
        this.columns = columns;
    }

    public String getName() {
        return name;
    }

    public Integer getColumns() {
        return columns;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public void setColumns(Integer columns) {
        this.columns = columns;
    }

    @Override
    public String toString() {
        return String.format("Room %s with %d seats, %d rows and %d columns", name, rows * columns, rows, columns);
    }
}