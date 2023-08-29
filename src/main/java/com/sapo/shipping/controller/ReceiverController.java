package com.sapo.shipping.controller;

import com.sapo.shipping.service.impl.ReceiverService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = "/api/receiver")
public class ReceiverController {
    private final ReceiverService receiverService;

    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }
}
