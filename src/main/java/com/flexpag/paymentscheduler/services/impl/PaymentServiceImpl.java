package com.flexpag.paymentscheduler.services.impl;

import com.flexpag.paymentscheduler.common.exception.EmptyFieldException;
import com.flexpag.paymentscheduler.common.exception.InvalidDateException;
import com.flexpag.paymentscheduler.common.exception.NotFoundException;
import com.flexpag.paymentscheduler.common.exception.PaymentStatusException;
import com.flexpag.paymentscheduler.domain.entities.Payment;
import com.flexpag.paymentscheduler.domain.enums.Status;
import com.flexpag.paymentscheduler.repositories.PaymentRepository;
import com.flexpag.paymentscheduler.rest.dto.PaymentDTO;
import com.flexpag.paymentscheduler.services.IPaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class PaymentServiceImpl implements IPaymentService {

    private final PaymentRepository paymentRepository;

    @Override
    public PaymentDTO findById(Integer id) {
        Optional<Payment> payment = paymentRepository.findById(id);

        if (!payment.isPresent()) {
            throw new NotFoundException("Payment scheduled or paid not found. id: " + id);
        }

        return convertPaymentIntoPaymentDTO(payment.get());
    }

    @Override
    @Transactional
    public PaymentDTO create(PaymentDTO paymentDto) {
        if (paymentDto.getAmount() == null) {
            throw new EmptyFieldException("Field (amount) required.");
        }

        paymentDto.setStatus(String.valueOf(Status.PENDING));
        Payment newPayment = convertPaymentDTOIntoPayment(paymentDto);
        Payment savedPayment = paymentRepository.save(newPayment);

        return convertPaymentIntoPaymentDTO(savedPayment);
    }

    @Override
    public PaymentDTO update(Integer id, PaymentDTO dto) {
        if (dto.getScheduled().isEmpty()) {
            throw new EmptyFieldException("Field (scheduled) required.");
        }
        PaymentDTO payment = findById(id);
        statusIsPaid(payment);

        Payment convertPayment = convertPaymentDTOIntoPayment(payment);
        convertPayment.setScheduled(convertStringToDate(dto.getScheduled()));
        Payment paymentSave = paymentRepository.saveAndFlush(convertPayment);

        return convertPaymentIntoPaymentDTO(paymentSave);
    }

    public PaymentDTO updateStatus(Integer id, PaymentDTO dto) {
        PaymentDTO payment = findById(id);
        if (dto.getStatus().isEmpty()) {
            throw new EmptyFieldException("Field (status) required (PENDING OR PAID).");
        }
        payment.setStatus(String.valueOf(dto.getStatus()));
        Payment convertPayment = convertPaymentDTOIntoPayment(payment);

        Payment paymentSave = paymentRepository.saveAndFlush(convertPayment);

        return convertPaymentIntoPaymentDTO(paymentSave);
    }

    @Override
    public void delete(Integer id) {
        PaymentDTO payment = findById(id);
        statusIsPaid(payment);
        paymentRepository.deleteById(id);
    }

    public Instant convertStringToDate(String date) {
       try {
           if (date != null) {
               Date newDate = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(date);
               return newDate.toInstant();
           }
           String dateNow = Instant.now().toString();
           Date format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(dateNow);
           return format.toInstant();

       } catch (ParseException e) {
           throw new InvalidDateException("Invalid date! required pattern is (yyyy-MM-dd'T'HH:mm:ss)");
       }
    }

    public PaymentDTO convertPaymentIntoPaymentDTO(Payment paymentEntity) {
        return new PaymentDTO().builder()
                .id(paymentEntity.getId())
                .requestDate(paymentEntity.getRequestDate().toString())
                .scheduled(paymentEntity.getScheduled().toString())
                .amount(paymentEntity.getAmount())
                .status(String.valueOf(paymentEntity.getStatus()))
                .build();
    }

    public Payment convertPaymentDTOIntoPayment(PaymentDTO paymentDTO) {
        return new Payment().builder()
                .id(paymentDTO.getId())
                .requestDate(convertStringToDate(paymentDTO.getRequestDate()))
                .scheduled(convertStringToDate(paymentDTO.getScheduled()))
                .amount(paymentDTO.getAmount())
                .status(Status.valueOf(paymentDTO.getStatus()))
                .build();
    }

    private void statusIsPaid(PaymentDTO payment) {
        if (payment.getStatus().equals(String.valueOf(Status.PAID))) {
            throw new PaymentStatusException("Cannot delete or update! Payment has already been made.");
        }
    }

}
