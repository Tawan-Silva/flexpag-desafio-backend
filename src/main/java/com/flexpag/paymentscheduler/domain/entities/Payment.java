package com.flexpag.paymentscheduler.domain.entities;

import com.flexpag.paymentscheduler.domain.enums.Status;
import com.flexpag.paymentscheduler.rest.dto.PaymentDTO;
import jdk.jshell.Snippet;
import lombok.*;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Builder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "tb_payment")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "request_date")
    private Instant requestDate;

    @Column(name = "scheduled_payment")
    private Instant scheduled;

    @Column(precision = 20, scale = 2)
    private BigDecimal amount;

    @Enumerated(EnumType.STRING)
    private Status status;

}
