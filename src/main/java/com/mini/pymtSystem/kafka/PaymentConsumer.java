package com.mini.pymtSystem.kafka;

import com.mini.pymtSystem.dto.PaymentInitiationEvent;
import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.repository.PymtDetailsRepository;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class PaymentConsumer {

    //dependency injection
    private final PymtDetailsRepository repository;

    public PaymentConsumer(PymtDetailsRepository repository){
        this.repository = repository;
    }

    @KafkaListener(
            topics = "vijay-pymt-test",
            groupId = "payment-group")

    public void consume(PaymentInitiationEvent event){
        System.out.println("received message from kafka");

        //create db entity
        PymtDetails payment = new PymtDetails();
        payment.setDebitAcct(event.getDebitAcct());
        payment.setDebitCCY(event.getDebitCCY());
        payment.setPymtStatus(event.getPymtStatus());

        //Save to repo
        repository.save(payment);
        System.out.println("Saved into DB");
    }

}
