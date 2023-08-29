package com.sapo.shipping.service.impl;

import com.sapo.shipping.repository.ReceiverRepository;
import com.sapo.shipping.service.IReceiverService;
import org.springframework.stereotype.Service;

@Service
public class ReceiverService implements IReceiverService {
    private final ReceiverRepository receiverRepository;

    public ReceiverService(ReceiverRepository receiverRepository) {
        this.receiverRepository = receiverRepository;
    }
}
