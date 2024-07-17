package com.project.model.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ConcertDTO {

    private int idConcert;

    private String city;

    private String band;

    private LocalDate reply;

    private BigDecimal unitPrice;

    private int qtyAvailable;

}
