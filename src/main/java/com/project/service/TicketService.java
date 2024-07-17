package com.project.service;

import com.project.model.Concert;
import com.project.model.Ticket;
import com.project.model.User;
import com.project.model.dto.ConcertDTO;
import com.project.model.dto.TicketDTO;
import com.project.model.dto.UserDTO;
import com.project.model.enums.StatusOrder;
import com.project.model.enums.TypePayment;
import com.project.repository.ConcertRepository;
import com.project.repository.TicketRepository;
import com.project.repository.UserRepository;
import com.project.service.interfaces.TicketFunctions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TicketService implements TicketFunctions {

    @Autowired
    private TicketRepository ticketRepository;

    @Autowired
    private ConcertService concertService;

    @Autowired
    private ConcertRepository concertRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public StatusOrder addTicket(Integer idUser, int idConcert, int ticketsQty, TypePayment typePayment) {
        //		CONTROLLO POSTI DISPONIBILI

        if(!concertService.checkAvailablePlaces(idConcert, ticketsQty)) {

            return StatusOrder.QTY_NOT_AVAILABLE;

        }


        Concert concert = concertRepository.findById(idConcert).get();

        User user = userRepository.findById(idUser).get();
        
        //		CONTROLLO SE ESISTE GIA UNA PRENOTAZIONE DI QUESTO CONCERTO PER QUESTO UTENTE IN TAL CASO AGGIORNO SOLO LA QUANTITA E IL PREZZO TOTALE

        if(existTicket(idUser, idConcert)) {

            Ticket ticketToUpdate = ticketRepository.findByUserIdAndConcertIdConcert(idUser, idConcert).get();

            ticketToUpdate.setTicketsQty(ticketToUpdate.getTicketsQty() + ticketsQty);
            ticketToUpdate.setTotalPrice(ticketToUpdate.getTotalPrice().add(concert.getUnitPrice().multiply(BigDecimal.valueOf(ticketsQty))));
            ticketRepository.save(ticketToUpdate);


            //			AGGIORNO ANCHE I POSTI DISPONIBILI DEL CONCERTO

            concertService.updateAvailablePlaces(idConcert, ticketsQty);

            return StatusOrder.UPDATED_SUCCESSFULY;

        }else {

            //			IN CASO CONTRARIO NE CREO UNA NUOVA

            Ticket newTicket = new Ticket();

            newTicket.setUser(user);
            newTicket.setConcert(concert);
            newTicket.setTicketsQty(ticketsQty);
            newTicket.setTotalPrice(concert.getUnitPrice().multiply(BigDecimal.valueOf(ticketsQty)));
            newTicket.setTypePayment(typePayment);

            ticketRepository.save(newTicket);

            //			AGGIORNO ANCHE I POSTI DISPONIBILI DEL CONCERTO

            concertService.updateAvailablePlaces(idConcert, ticketsQty);

            return StatusOrder.ADDED_SUCCESSFULY;

        }

    }

    @Override
    public boolean existTicket(Integer idUser, int idConcert) {

        return ticketRepository.findByUserIdAndConcertIdConcert(idUser, idConcert).isPresent();

    }

    @Override
    public Optional<TicketDTO> getTicket(int idTicket) {

        Optional<Ticket> ticketOptional = ticketRepository.findById(idTicket);

        if(ticketOptional.isEmpty()){
            return Optional.empty();
        }
        Ticket ticket = ticketOptional.get();

        ConcertDTO ConcertDTO = concertService.getConcert(ticket.getConcert().getIdConcert()).get();

        User user = userRepository.findById(ticket.getUser().getId()).get();

        UserDTO userDTO = UserDTO.builder()
                .id(user.getId())
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();

        TicketDTO ticketDTO = TicketDTO.builder()
                .idTicket(ticket.getIdTicket())
                .concert(ConcertDTO)
                .user(userDTO)
                .ticketsQty(ticket.getTicketsQty())
                .typePayment(ticket.getTypePayment())
                .totalPrice(ticket.getTotalPrice())
                .build();

        return Optional.ofNullable(ticketDTO);
    }

    @Override
    public List<TicketDTO> getTickets(Integer idUser) {

        List<Ticket> tickets = ticketRepository.findByUserId(idUser);

        List<TicketDTO> ticketDTOS = new ArrayList<>();

        User user = userRepository.findById(idUser).get();

        UserDTO userDTO = UserDTO.builder()
                .id(idUser)
                .name(user.getName())
                .surname(user.getSurname())
                .email(user.getEmail())
                .build();

        for (Ticket ticket : tickets){

            ConcertDTO ConcertDTO = concertService.getConcert(ticket.getConcert().getIdConcert()).get();

            TicketDTO ticketDTO = TicketDTO.builder()
                    .idTicket(ticket.getIdTicket())
                    .concert(ConcertDTO)
                    .user(userDTO)
                    .ticketsQty(ticket.getTicketsQty())
                    .typePayment(ticket.getTypePayment())
                    .totalPrice(ticket.getTotalPrice())
                    .build();

            ticketDTOS.add(ticketDTO);

        }

        return ticketDTOS;
    }
}
