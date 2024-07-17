package com.project.repository;

import com.project.model.Concert;
import com.project.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ConcertRepository  extends JpaRepository<Concert, Integer> {

    @Query("SELECT c FROM Concert c WHERE c.qtyAvailable > 0 AND c.reply >= :concertDate")
    List<Concert> findAvailableConcertsByDate(@Param("concertDate") LocalDate concertDate);

}
