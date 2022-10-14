package com.flexpag.paymentscheduler.services;

import com.flexpag.paymentscheduler.rest.dto.PaymentDTO;

public interface IPaymentService {

    PaymentDTO findById(Integer id);
    PaymentDTO create(PaymentDTO dto);

    PaymentDTO update(Integer id, PaymentDTO dto);
    void delete(Integer id);
}
