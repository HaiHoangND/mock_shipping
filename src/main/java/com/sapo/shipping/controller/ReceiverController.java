package com.sapo.shipping.controller;


import com.sapo.shipping.dto.ReceiverDto;
import com.sapo.shipping.response.GeneralResponse;
import com.sapo.shipping.service.impl.ReceiverService;
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

    @GetMapping("/getByShopOwnerId")
    GeneralResponse<?> getByShopOwnerId(@RequestParam Integer shopOwnerId,@RequestParam int pageNumber, @RequestParam int pageSize, @RequestParam(required = false) String keyWord) {
        return GeneralResponse.ok("success", " Receiver Successfully fetched",receiverService.getReceiverByShopOwnerId(shopOwnerId, pageNumber, pageSize, keyWord));
    }

    @PostMapping
    GeneralResponse<?> createReceiver(@RequestBody ReceiverDto receiverDto) {
        try {
            return GeneralResponse.ok("success", "Receiver Successfully created", receiverService.createReceiver(receiverDto));
        } catch (Exception e) {
            return GeneralResponse.failed("failed", e.getMessage());
        }
    }

    @PutMapping("/{id}")
    GeneralResponse<?> updateReceiver(@PathVariable Integer id, @RequestBody ReceiverDto receiverDto) {
        return GeneralResponse.ok("success", " Receiver Successfully updated",receiverService.updateReceiver(id, receiverDto));
    }

    @DeleteMapping("/{id}")
    GeneralResponse<?> deleteReceiver(@PathVariable Integer id) {
        return GeneralResponse.ok("success",  "Receiver Successfully deleted",receiverService.deleteReceiverById(id));
    }
}
