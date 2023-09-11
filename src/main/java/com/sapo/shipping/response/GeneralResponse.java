package com.sapo.shipping.response;

import lombok.*;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class GeneralResponse<T> implements Serializable {

    private String type;
    private String message;
    private T data;

    public static GeneralResponse<?> ok(String type,String message, Object data) {
        return GeneralResponse.builder()
                .type(type)
                .message(message)
                .data(data)
                .build();
    }

    public static GeneralResponse<?> failed(String type,String message) {
        return GeneralResponse.builder()
                .type(type)
                .message(message)
                .build();
    }

}
