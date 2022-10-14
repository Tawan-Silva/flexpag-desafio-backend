package com.flexpag.paymentscheduler.rest.dto;

import lombok.*;

import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@Builder(toBuilder = true)
@AllArgsConstructor@NoArgsConstructor
public class PaymentDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String requestDate;

    @NotNull(message = "{field.scheduled.required}")
    private String scheduled;

    private BigDecimal amount;
    private String status;
}
