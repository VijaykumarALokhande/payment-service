package com.mini.pymtSystem.controller;

import com.mini.pymtSystem.entity.PymtDetails;
import com.mini.pymtSystem.service.PymtService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PymtController {

    private final PymtService pymtService;

    public PymtController(PymtService pymtService) {
        this.pymtService = pymtService;
    }
    // Spring injects Producer bean

    @PostMapping
    public PymtDetails createPymt(@RequestBody PymtDetails pymtDetails) {
        return pymtService.createPaymet(pymtDetails);
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
