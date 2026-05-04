package com.webperside.deliverycollectionsystem.model.enums.response;

import org.springframework.http.HttpStatus;

public interface ResponseMessages {

    String key();

    String message();

    HttpStatus status();

}
