package com.epam.training.ticketservice.service;

import com.epam.training.ticketservice.entities.Screening;

public interface ScreeningService {

    void saveScreening(Screening screening);


    void deleteScreening(String movieName, String roomName, String customDate);


    StringBuilder listScreenings();
}
