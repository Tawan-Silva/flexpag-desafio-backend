package com.flexpag.paymentscheduler.rest.controller;

import com.flexpag.paymentscheduler.common.exception.EmptyFieldException;
import com.flexpag.paymentscheduler.rest.dto.PaymentDTO;
import com.flexpag.paymentscheduler.services.impl.PaymentServiceImpl;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RequiredArgsConstructor
@RestController
@RequestMapping("payments")
public class PaymentController {

    private final PaymentServiceImpl paymentService;

    @ApiOperation(value = "Save schedule a payment")
    @PostMapping
    public ResponseEntity registerPayment(@RequestBody @Valid PaymentDTO paymentDTO) {
        PaymentDTO obj = paymentService.create(paymentDTO);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri()
                .buildAndExpand(obj.getId()).toUri();

        return ResponseEntity.created(uri).body(obj);
    }

    @ApiOperation(value = "Return payment information")
    @GetMapping("/{id}")
    public ResponseEntity getPayment(@PathVariable Integer id) {
        return ResponseEntity.ok().body(paymentService.findById(id));
    }

    @ApiOperation(value = "Update schedule payment")
    @PutMapping("/{id}")
    public ResponseEntity<PaymentDTO> updateSheduledPayment(@PathVariable Integer id, @Valid @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updated = paymentService.update(id, paymentDTO);
        return ResponseEntity.ok(updated);
    }

    @ApiOperation(value = "Update status payment")
    @PutMapping("/updateStatus/{id}")
    public ResponseEntity<PaymentDTO> updateStatus(@PathVariable Integer id, @RequestBody PaymentDTO paymentDTO) {
        PaymentDTO updated = paymentService.updateStatus(id, paymentDTO);
        return ResponseEntity.ok(updated);
    }

    @ApiOperation(value = "Delete one payment")
    @DeleteMapping("/{id}")
    public ResponseEntity delete(@PathVariable Integer id) {
        paymentService.delete(id);
        return ResponseEntity.ok().build();
    }


}
