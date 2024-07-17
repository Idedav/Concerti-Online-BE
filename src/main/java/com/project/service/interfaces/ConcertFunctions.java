package com.project.service.interfaces;

import com.project.model.Concert;
import com.project.model.dto.ConcertDTO;

import java.util.List;
import java.util.Optional;

public interface ConcertFunctions {

    List<ConcertDTO> getConcertAvailable();

    Optional<ConcertDTO> getConcert(int idConcert);

    Concert updateAvailablePlaces(int idConcert, int ticketsQty);

    boolean checkAvailablePlaces(int idConcert, int ticketsQty);
}

