package com.project.service;


import com.project.model.Concert;
import com.project.model.dto.ConcertDTO;
import com.project.repository.ConcertRepository;
import com.project.service.interfaces.ConcertFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ConcertService implements ConcertFunctions {

    @Autowired
    private ConcertRepository concertRepository;

    @Override
    public List<ConcertDTO> getConcertAvailable() {

        List<Concert> concerts =concertRepository.findAvailableConcertsByDate(LocalDate.now());

        List<ConcertDTO> concertDTOS = new ArrayList<>();

        for (Concert concert : concerts){

            ConcertDTO concertDTO = ConcertDTO.builder()
                    .idConcert(concert.getIdConcert())
                    .city(concert.getCity())
                    .band(concert.getBand())
                    .reply(concert.getReply())
                    .unitPrice(concert.getUnitPrice())
                    .qtyAvailable(concert.getQtyAvailable())
                    .build();

            concertDTOS.add(concertDTO);
        }
        return concertDTOS;
    }

    @Override
    public Optional<ConcertDTO> getConcert(int idConcert) {

        Optional<Concert> concertOpt = concertRepository.findById(idConcert);

        if(concertOpt.isEmpty()){
            return Optional.empty();
        }

        ConcertDTO concertDTO = ConcertDTO.builder()
                .idConcert(concertOpt.get().getIdConcert())
                .city(concertOpt.get().getCity())
                .band(concertOpt.get().getBand())
                .reply(concertOpt.get().getReply())
                .unitPrice(concertOpt.get().getUnitPrice())
                .qtyAvailable(concertOpt.get().getQtyAvailable())
                .build();


        return Optional.ofNullable(concertDTO);
    }

    @Override
    public Concert updateAvailablePlaces(int idConcert, int ticketsQty) {

        Concert concertToUpdate = concertRepository.findById(idConcert).get();

        concertToUpdate.setQtyAvailable(concertToUpdate.getQtyAvailable() - ticketsQty);

        concertRepository.save(concertToUpdate);

        return concertToUpdate;
    }

    @Override
    public boolean checkAvailablePlaces(int idConcert, int ticketsQty) {

        Concert concert = concertRepository.findById(idConcert).get();

        return concert.getQtyAvailable() - ticketsQty <= -1 ? false : true;

    }
}
