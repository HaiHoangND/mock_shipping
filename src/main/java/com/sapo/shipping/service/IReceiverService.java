package com.sapo.shipping.service;

import com.sapo.shipping.dto.ReceiverDto;
import com.sapo.shipping.dto.ReceiverWithOrders;
import com.sapo.shipping.entity.Receiver;
import org.springframework.data.domain.Page;


import java.util.List;

public interface IReceiverService {
    List<Receiver> getAllReceivers();

    Receiver getReceiverById(int id);

    Receiver createReceiver(ReceiverDto receiverDto);

    List<Receiver> deleteReceiverById(Integer id);

    Receiver updateReceiver(Integer id, ReceiverDto receiverDto);

    Page<ReceiverWithOrders> getReceiverByShopOwnerId(Integer shopOwnerId, int pageNumber, int pageSize, String keyWord);
}
