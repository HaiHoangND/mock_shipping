package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ReceiverDto;
import com.sapo.shipping.entity.Receiver;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.ReceiverMapper;
import com.sapo.shipping.repository.ReceiverRepository;
import com.sapo.shipping.service.IReceiverService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiverService implements IReceiverService {
    private final ReceiverRepository receiverRepository;
    private final ReceiverMapper mapper;
    private final Validator validator;


    public ReceiverService(ReceiverRepository receiverRepository, ReceiverMapper mapper, Validator validator) {
        this.receiverRepository = receiverRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<Receiver> getAllReceivers() {
        return receiverRepository.findAll();
    }

    @Override
    public Receiver getReceiverById(int id) {
        return receiverRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Receiver not found"));
    }

    @Override
    public Receiver createReceiver(ReceiverDto receiverDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(receiverDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400","error", errors.get(0));
        }
        Receiver receiver = mapper.createEntity(receiverDto);
        return receiverRepository.save(receiver);
    }

    @Override

    public Receiver updateReceiver(Integer id, ReceiverDto receiverDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(receiverDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400","error", errors.get(0));
        }
        Receiver receiver = receiverRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Receiver not found"));
        mapper.updateEntity(receiver, receiverDto);
        return receiverRepository.save(receiver);
    }

    @Override
    public List<Receiver> deleteReceiverById(Integer id) {
        receiverRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Receiver not found"));
        return receiverRepository.findAll();
    }
}
