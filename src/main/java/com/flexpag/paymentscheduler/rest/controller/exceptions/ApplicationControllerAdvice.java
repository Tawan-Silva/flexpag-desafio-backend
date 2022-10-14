package com.flexpag.paymentscheduler.rest.controller.exceptions;

import com.flexpag.paymentscheduler.common.exception.EmptyFieldException;
import com.flexpag.paymentscheduler.common.exception.NotFoundException;
import com.flexpag.paymentscheduler.common.exception.PaymentStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;
import java.time.Instant;
import java.util.List;
import java.util.stream.Collectors;

@RestControllerAdvice
public class ApplicationControllerAdvice {


    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<StandardError> handleNotFoundException(NotFoundException ex, HttpServletRequest req) {
        String error = "Not Found";
        HttpStatus status = HttpStatus.NOT_FOUND;
        StandardError err =
                new StandardError(Instant.now(), status.value(), error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(PaymentStatusException.class)
    public ResponseEntity<StandardError> handlePaymentStatusException(
            PaymentStatusException ex, HttpServletRequest req) {
        String error = "Not Found";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err =
                new StandardError(Instant.now(), status.value(), error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity hadleMethodArgumentNotValidException(
            MethodArgumentNotValidException ex, HttpServletRequest req) {

        List<String> errors = ex.getBindingResult().getAllErrors().stream()
                .map(e -> e.getDefaultMessage()).collect(Collectors.toList());


        String error = "Fields Required";
        HttpStatus status = HttpStatus.BAD_REQUEST;

        ApiErrorsFields err =
                new ApiErrorsFields(Instant.now(), status.value(), error, errors, req.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

    @ExceptionHandler(EmptyFieldException.class)
    public ResponseEntity<StandardError> handleEmptyFieldNullException(EmptyFieldException ex, HttpServletRequest req) {
        String error = "Fields Required";
        HttpStatus status = HttpStatus.BAD_REQUEST;
        StandardError err =
                new StandardError(Instant.now(), status.value(), error, ex.getMessage(), req.getRequestURI());

        return ResponseEntity.status(status).body(err);
    }

}
