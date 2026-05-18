package com.webperside.deliverycollectionsystem.model.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class OtpDto {

    private Long id;
    private String otpCode;

}
