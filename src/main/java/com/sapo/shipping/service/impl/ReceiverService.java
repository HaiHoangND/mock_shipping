package com.sapo.shipping.service.impl;

import com.sapo.shipping.dto.ReceiverDto;
import com.sapo.shipping.dto.ReceiverWithOrders;
import com.sapo.shipping.entity.Receiver;
import com.sapo.shipping.exception.BusinessException;
import com.sapo.shipping.mapper.ReceiverMapper;
import com.sapo.shipping.repository.ReceiverRepository;
import com.sapo.shipping.repository.ShippingOrderRepository;
import com.sapo.shipping.service.IReceiverService;
import jakarta.transaction.Transactional;
import jakarta.validation.Validator;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ReceiverService implements IReceiverService {
    private final ReceiverRepository receiverRepository;
    private final ReceiverMapper mapper;
    private final Validator validator;

    private final ShippingOrderRepository shippingOrderRepository;
    public ReceiverService(ShippingOrderRepository shippingOrderRepository,ReceiverRepository receiverRepository, ReceiverMapper mapper, Validator validator) {
        this.receiverRepository = receiverRepository;
        this.mapper = mapper;
        this.validator = validator;
        this.shippingOrderRepository = shippingOrderRepository;
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
    public Page<ReceiverWithOrders> getReceiverByShopOwnerId(Integer shopOwnerId, int pageNumber, int pageSize, String keyWord){
        PageRequest pageRequest = PageRequest.of(pageNumber - 1, pageSize);
        Page<Receiver> receiverPage = receiverRepository.getReceiversByShopOwnerId(shopOwnerId, pageRequest, keyWord);
        List<ReceiverWithOrders> receiversWithOrders = new ArrayList<>();
        for(Receiver receiver : receiverPage.getContent()){
            ReceiverWithOrders receiverWithOrders = new ReceiverWithOrders(receiver);
            int numberOfOrders = shippingOrderRepository.countShippingOrdersByReceiverId(receiver.getId());
            int successfulOrders = shippingOrderRepository.countSuccessfulShippingOrdersByReceiverId(receiver.getId());
            receiverWithOrders.setNumberOfOrders(numberOfOrders);
            receiverWithOrders.setSuccessfulOrders(successfulOrders);
            receiversWithOrders.add(receiverWithOrders);
        }
        return new PageImpl<>(receiversWithOrders, pageRequest, receiverPage.getTotalElements());
    };

    @Override
    @Transactional(rollbackOn = Exception.class)
    public Receiver createReceiver(ReceiverDto receiverDto) {
        List<String> errors = new ArrayList<>();
        validator.validate(receiverDto)
                .forEach(e -> errors.add(e.getMessage()));
        if (!errors.isEmpty()) {
            throw new BusinessException("400","error", errors.get(0));
        }
        String phone = receiverDto.getPhone();
        String address = receiverDto.getAddress();
        int shopOwnerId = receiverDto.getShopOwnerId();
        if(receiverRepository.findByPhoneAndAddress(phone, address, shopOwnerId) != null){
            throw new BusinessException("400", "error", "Receiver existed");
        }
        Receiver receiver = mapper.createEntity(receiverDto);
        return receiverRepository.save(receiver);
    }

    @Override
    @Transactional(rollbackOn = Exception.class)
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
    @Transactional(rollbackOn = Exception.class)
    public List<Receiver> deleteReceiverById(Integer id) {
        receiverRepository.findById(id)
                .orElseThrow(() -> new BusinessException("404", "error", "Receiver not found"));
        return receiverRepository.findAll();
    }
}
