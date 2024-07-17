package com.project.model.request;

import com.project.model.enums.TypePayment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RequestTicket {

    private Integer idUser;

    private int idConcert;

    private int ticketsQty;

    private TypePayment typePayment;

}
