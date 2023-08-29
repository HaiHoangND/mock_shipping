package com.sapo.shipping.controller;


import com.sapo.shipping.dto.ReceiverDto;
import com.sapo.shipping.dto.SenderDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ReceiverService;
import com.sapo.shipping.service.impl.SenderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/receiver")
public class ReceiverController {
    private final ReceiverService receiverService;

    public ReceiverController(ReceiverService receiverService) {
        this.receiverService = receiverService;
    }

    @GetMapping
    GeneralResponse<?> getAllReceivers() {
        return GeneralResponse.ok("success", " Receivers Successfully fetched",receiverService.getAllReceivers());
    }

    @GetMapping("/{id}")
    GeneralResponse<?> getReceiverById(@PathVariable Integer id) {
        return GeneralResponse.ok("success", " Receiver Successfully fetched",receiverService.getReceiverById(id));
    }

    @PostMapping("/create")
    GeneralResponse<?> createReceiver(@RequestBody ReceiverDto receiverDto) {
        return GeneralResponse.ok("success", " Receiver Successfully created",receiverService.createReceiver(receiverDto));
    }

    @PutMapping("/update/{id}")
    GeneralResponse<?> updateReceiver(@PathVariable Integer id, @RequestBody ReceiverDto receiverDto) {
        return GeneralResponse.ok("success", " Receiver Successfully updated",receiverService.updateReceiver(id, receiverDto));
    }

    @DeleteMapping("/delete/{id}")
    GeneralResponse<?> deleteReceiver(@PathVariable Integer id) {
        return GeneralResponse.ok("success",  "Receiver Successfully deleted",receiverService.deleteReceiverById(id));
    }
}
