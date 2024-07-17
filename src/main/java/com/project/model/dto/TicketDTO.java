package com.project.model.dto;


import com.project.model.enums.TypePayment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TicketDTO {

    private int idTicket;

    private ConcertDTO concert;

    private  UserDTO user;

    private int ticketsQty;

    private TypePayment typePayment;

    private BigDecimal totalPrice;

}
