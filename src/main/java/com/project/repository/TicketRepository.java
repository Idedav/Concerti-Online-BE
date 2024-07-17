package com.project.repository;

import com.project.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TicketRepository  extends JpaRepository<Ticket, Integer> {

    Optional<Ticket> findByUserIdAndConcertIdConcert(Integer idUser, int idConcert);

    List<Ticket> findByUserId(Integer idUser);

}
