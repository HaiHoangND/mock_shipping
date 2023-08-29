package com.sapo.shipping.exception;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BusinessException extends RuntimeException {

    private String code;
    private String type;
    private String message;


}
