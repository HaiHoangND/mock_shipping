package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.SenderDto;
import com.sapo.shipping.entity.Sender;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.SenderMapper;
import com.sapo.shipping.repository.SenderRepository;
import com.sapo.shipping.service.ISenderService;
import jakarta.validation.Validator;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SenderService implements ISenderService {
    private final SenderRepository senderRepository;
    private final SenderMapper mapper;
    private final Validator validator;


    public SenderService(SenderRepository senderRepository, SenderMapper mapper, Validator validator) {
        this.senderRepository = senderRepository;
        this.mapper = mapper;
        this.validator = validator;
    }

    @Override
    public List<Sender> getAllSenders() {
        return senderRepository.findAll();
    }

    @Override
    public Sender getSenderById(int id) {
        return senderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Sender not found"));
    }

    @Override
    public Sender createSender(SenderDto senderDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(senderDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400","error", errors.get(0));
        }
        Sender sender = mapper.createEntity(senderDto);
        return senderRepository.save(sender);
    }

    @Override

    public Sender updateSender(Integer id, SenderDto senderDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(senderDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400","error", errors.get(0));
        }
        Sender sender = senderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Sender not found"));
        mapper.updateEntity(sender, senderDto);
        return senderRepository.save(sender);
    }

    @Override
    public List<Sender> deleteSenderById(Integer id) {
        senderRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Sender not found"));
        return senderRepository.findAll();
    }
}
