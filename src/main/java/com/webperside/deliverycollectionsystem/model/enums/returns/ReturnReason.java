package com.webperside.deliverycollectionsystem.model.enums.returns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReturnReason {

    CUSTOMER_REJECTED("Müştəri imtina etdi"),
    DELIVERY_FAILED("Çatdırılma mümkün olmadı"),
    WRONG_ADDRESS("Yanlış ünvan"),
    CUSTOMER_UNREACHABLE("Müştəri ilə əlaqə mümkün olmadı"),
    DAMAGED_PACKAGE("Bağlama zədələnib"),
    OTHER("Digər");

    private final String description;

}
