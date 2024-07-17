package com.project.model;


import com.project.model.enums.TypePayment;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@Table(name = "tickets")
public class Ticket implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name= "id")
    private int idTicket;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "concert_id")
    private Concert concert;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Column(name= "tickets_qty")
    private int ticketsQty;

    @Column(name= "type_payment")
    @Enumerated(EnumType.STRING)
    private TypePayment typePayment;

    @Column(name= "total_price", precision = 10, scale = 2)
    private BigDecimal totalPrice;




}
