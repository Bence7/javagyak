package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entities.Screening;

import java.util.List;


public interface ScreeningService {
    void saveScreening(Screening screening);

    void deleteScreening(String movieName, String roomName, String customDate);

    List<Screening> findAll();
}
