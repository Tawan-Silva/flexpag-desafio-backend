package com.flexpag.paymentscheduler.unitary;

import com.flexpag.paymentscheduler.common.PaymentMock;
import com.flexpag.paymentscheduler.domain.entities.Payment;
import com.flexpag.paymentscheduler.domain.enums.Status;
import com.flexpag.paymentscheduler.repositories.PaymentRepository;
import com.flexpag.paymentscheduler.rest.dto.PaymentDTO;
import com.flexpag.paymentscheduler.services.impl.PaymentServiceImpl;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class PaymentServiceTest {

    public static final Integer ID            = 1;
    public static String REQUEST_DATE = null;
    public static final String SCHEDULED      = convertStringToDate("2022-10-13").toString();
    public static final BigDecimal AMOUNT     = BigDecimal.valueOf(200.00);
    public static final String STATUS         = String.valueOf(Status.PENDING);
    private final PaymentMock paymentMock = new PaymentMock();
    private PaymentDTO paymentDTO = new PaymentDTO();
    private Payment paymentEntity = new Payment();

    @InjectMocks
    PaymentServiceImpl paymentService;

    @Mock
    PaymentRepository paymentRepository;

    @BeforeEach
    void init() {
        MockitoAnnotations.openMocks(this);
        startCreatePayment();
    }
/*
    @Test
    @Order(1)
    void savePaymentSuccessTest() {
        PaymentDTO request = paymentMock.getPaymentRequestDTO();
        Payment response = paymentMock.getPayment();

        when(paymentRepository.save(any(Payment.class))).thenReturn(paymentEntity);

        System.out.println("Request " + request);
        PaymentDTO payment = paymentService.create(paymentDTO);

        assertNotEquals(0, response.getId());
        assertEquals(payment.getRequestDate(), response.getRequestDate());
        assertEquals(payment.getScheduled(), response.getScheduled());
        assertEquals(payment.getAmount(), response.getAmount());
        assertEquals(payment.getStatus(), response.getStatus());
    }
*/
    public static Instant convertStringToDate(String date) {

        try {
            if (date != null) {
                Date newDate = new SimpleDateFormat("yyyy-MM-dd").parse(date);
                return newDate.toInstant();
            }
            return Instant.now();
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    private void startCreatePayment() {
        paymentDTO = new PaymentDTO(ID, REQUEST_DATE, SCHEDULED, AMOUNT, STATUS);
        paymentEntity = new Payment(
                ID,
                convertStringToDate(REQUEST_DATE),
                convertStringToDate(SCHEDULED),
                AMOUNT,
                Status.valueOf(STATUS));
    }

}
