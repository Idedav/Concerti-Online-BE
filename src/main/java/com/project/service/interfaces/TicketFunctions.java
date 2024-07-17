package com.project.service.interfaces;

import com.project.model.Ticket;
import com.project.model.dto.TicketDTO;
import com.project.model.enums.StatusOrder;
import com.project.model.enums.TypePayment;

import java.util.List;
import java.util.Optional;

public interface TicketFunctions {

    StatusOrder addTicket(Integer idUser, int idConcert, int ticketsQty, TypePayment typePayment);

    boolean existTicket(Integer idUser, int idConcert);

    Optional<TicketDTO> getTicket(int idTicket);

    List<TicketDTO> getTickets(Integer idUser);

}
