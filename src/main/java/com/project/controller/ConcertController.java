package com.project.controller;


import com.project.model.dto.ConcertDTO;
import com.project.service.ConcertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/concert-on/concert")
public class ConcertController {

    @Autowired
    private ConcertService concertService;

    @GetMapping("/")
    private ResponseEntity<List<ConcertDTO>> getConcertAvailable(){

        return new ResponseEntity<List<ConcertDTO>>(concertService.getConcertAvailable(), HttpStatus.OK);

    }

    @GetMapping("/{id_concert}")
    private ResponseEntity<ConcertDTO> getConcertAvailable(@PathVariable("id_concert") int idConcert){

        Optional<ConcertDTO> concertDTOOptional = concertService.getConcert(idConcert);

        if(concertDTOOptional.isEmpty()){
            return new ResponseEntity<ConcertDTO>(HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<ConcertDTO>(concertDTOOptional.get(), HttpStatus.OK);

    }


}
