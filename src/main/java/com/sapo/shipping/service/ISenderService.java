package com.sapo.shipping.service;

import com.sapo.shipping.dto.SenderDto;
import com.sapo.shipping.dto.WarehouseDto;
import com.sapo.shipping.entity.Sender;
import com.sapo.shipping.entity.Warehouse;

import java.util.List;

public interface ISenderService {
    List<Sender> getAllSenders();

    Sender getSenderById(int id);

    Sender createSender(SenderDto senderDto);

    List<Sender> deleteSenderById(Integer id);

    Sender updateSender(Integer id, SenderDto senderDto);
}
