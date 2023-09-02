package com.sapo.shipping.response;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class WarehouseStatsResponse<T> implements Serializable {
    private String type;
    private String message;
    private T data;

    public static WarehouseStatsResponse<?> ok(String type,String message, List<Object> data) {
        return WarehouseStatsResponse.builder()
                .type(type)
                .message(message)
                .data(data)
                .build();
    }
}
