package com.sapo.shipping.controller;


import com.sapo.shipping.dto.SenderDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.SenderService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/api/sender")
public class SenderController {
    private final SenderService senderService;

    public SenderController(SenderService senderService) {
        this.senderService = senderService;
    }

    @GetMapping
    GeneralResponse<?> getAllSenders() {
        return GeneralResponse.ok("success", " Senders Successfully fetched",senderService.getAllSenders());
    }

    @GetMapping("/{id}")
    GeneralResponse<?> getSenderById(@PathVariable Integer id) {
        return GeneralResponse.ok("success", "Sender Successfully fetched",senderService.getSenderById(id));
    }

    @PostMapping("/create")
    GeneralResponse<?> createSender(@RequestBody SenderDto senderDto) {
        return GeneralResponse.ok("success", " Sender Successfully created",senderService.createSender(senderDto));
    }

    @PutMapping("/update/{id}")
    GeneralResponse<?> updateSender(@PathVariable Integer id, @RequestBody SenderDto senderDto) {
        return GeneralResponse.ok("success", " Sender Successfully updated",senderService.updateSender(id, senderDto));
    }

    @DeleteMapping("/delete/{id}")
    GeneralResponse<?> deleteSender(@PathVariable Integer id) {
        return GeneralResponse.ok("success", " Sender Successfully deleted",senderService.deleteSenderById(id));
    }
}
