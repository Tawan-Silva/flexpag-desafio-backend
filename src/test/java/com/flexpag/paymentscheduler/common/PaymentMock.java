package com.flexpag.paymentscheduler.common;

import com.flexpag.paymentscheduler.domain.entities.Payment;
import com.flexpag.paymentscheduler.domain.enums.Status;
import com.flexpag.paymentscheduler.rest.dto.PaymentDTO;
import lombok.NonNull;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

public class PaymentMock {

    @NonNull
    public PaymentDTO getPaymentRequestDTO() {
        PaymentDTO requestDTO = new PaymentDTO();
        requestDTO.setId(Integer.valueOf(999));
        requestDTO.setRequestDate(String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        requestDTO.setScheduled(String.valueOf(LocalDateTime.now().toInstant(ZoneOffset.UTC)));
        requestDTO.setAmount(BigDecimal.valueOf(200.00));
        requestDTO.setStatus(String.valueOf(Status.PAID));

        System.out.println("DTO " + requestDTO);
        return requestDTO;
    }

    @NonNull
    public Payment getPayment() {
        Payment payment = new Payment();
        payment.setId(Integer.valueOf(888));
        payment.setRequestDate(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        payment.setScheduled(LocalDateTime.now().toInstant(ZoneOffset.UTC));
        payment.setAmount(BigDecimal.valueOf(400.00));
        payment.setStatus(Status.PENDING);
        return payment;
    }
}
