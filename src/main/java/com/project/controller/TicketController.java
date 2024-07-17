package com.project.controller;


import com.project.model.dto.ConcertDTO;
import com.project.model.dto.TicketDTO;
import com.project.model.enums.StatusOrder;
import com.project.model.request.RequestTicket;
import com.project.service.TicketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/concert-on/ticket")
public class TicketController {

    @Autowired
    private TicketService ticketService;

    @GetMapping("{id_ticket}")
    private ResponseEntity<TicketDTO> getTicket(@PathVariable("id_ticket") int idTicket){

        Optional<TicketDTO> ticketDTOOptional = ticketService.getTicket(idTicket);

        if(ticketDTOOptional.isEmpty()){
            return new ResponseEntity<TicketDTO>(ticketDTOOptional.get(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<TicketDTO>(ticketDTOOptional.get(), HttpStatus.OK);

    }

    @GetMapping("tickets/{id_user}")
    private ResponseEntity<List<TicketDTO>> getTickets(@PathVariable("id_user") Integer idUser){

        List<TicketDTO> tickets = ticketService.getTickets(idUser);

        return new ResponseEntity<List<TicketDTO>>(tickets, HttpStatus.OK);

    }

    @PostMapping("/")
    private ResponseEntity<StatusOrder> addTicket(@RequestBody RequestTicket requestTicket){

        StatusOrder statusOrder = ticketService.addTicket(requestTicket.getIdUser(),
                                                          requestTicket.getIdConcert(),
                                                          requestTicket.getTicketsQty(),
                                                          requestTicket.getTypePayment());

        if (statusOrder == statusOrder.QTY_NOT_AVAILABLE){
            return new ResponseEntity<StatusOrder>(statusOrder, HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<StatusOrder>(statusOrder, HttpStatus.OK);

    }
}
