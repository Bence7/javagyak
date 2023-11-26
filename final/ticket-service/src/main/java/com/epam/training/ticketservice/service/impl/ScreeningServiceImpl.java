package com.epam.training.ticketservice.service.impl;

import com.epam.training.ticketservice.entities.Screening;
import com.epam.training.ticketservice.repository.ScreeningRepository;
import com.epam.training.ticketservice.service.ScreeningService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScreeningServiceImpl implements ScreeningService {

    private final ScreeningRepository screeningRepository;

    public ScreeningServiceImpl(ScreeningRepository screeningRepository) {
        this.screeningRepository = screeningRepository;
    }

    @Override
    public void saveScreening(Screening screening) {
        screeningRepository.save(screening);
    }

    @Override
    public void deleteScreening(String movieName, String roomName, String customDate) {
        screeningRepository.findAll().forEach(screening -> {
            if (screening.getRoom().getName().equals(roomName)
                    && screening.getMovie().getName().equals(movieName)
                    && screening.getCustomDate().equals(customDate)) {
                screeningRepository.delete(screening);
            }
        });
    }


    /*StringBuilder screeningList = new StringBuilder();
        screeningRepository.findAll().forEach(movie -> screeningList.append(movie.toString()).append("\n"));
        if (screeningList.length() > 2) {
            screeningList.delete(screeningList.length() - 1, screeningList.length());
            return screeningList;
        } else {
            return new StringBuilder("There are no screenings");
        }*/

    @Override
    public List<Screening> findAll() {
        return screeningRepository.findAll();
    }
}
