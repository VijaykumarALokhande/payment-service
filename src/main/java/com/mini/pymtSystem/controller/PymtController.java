package com.mini.pymtSystem.controller;

import com.mini.pymtSystem.dto.PaymentInitiationEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.kafka.PaymentProducer;
import com.mini.pymtSystem.service.PymtService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PymtController {

    private final PymtService pymtService;
    private final PaymentProducer producer;

    public PymtController(PymtService pymtService, PaymentProducer producer) {
        this.pymtService = pymtService;
        this.producer = producer;
    }
    // Spring injects Producer bean

    @PostMapping
    public ResponseEntity<String> createPymt(@RequestBody PaymentInitiationEvent event) {

        //publish the even
        producer.publish(event);

        return ResponseEntity.accepted()
                .body("Payment Submitted");
    }

    @GetMapping("/{id}")
    public PymtDetails getById(@PathVariable Long id) {
        return pymtService.getPayment(id);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        pymtService.deletePayment(id);
    }

}
