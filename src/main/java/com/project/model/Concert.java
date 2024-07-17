package com.project.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "concerts")
public class Concert implements Serializable{

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name= "id")
    private int idConcert;

    @Column(name= "city")
    private String city;

    @Column(name= "band")
    private String band;

    @Column(name= "reply")
    @Temporal(TemporalType.DATE)
    private LocalDate reply;

    @Column(name= "unit_price", precision = 10, scale = 2)
    private BigDecimal unitPrice;

    @Column(name= "qty_available")
    private int qtyAvailable;

    @OneToMany(mappedBy = "concert", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Ticket> tickets = new ArrayList<>();

}
