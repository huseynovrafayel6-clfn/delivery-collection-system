package com.webperside.deliverycollectionsystem.model.enums.delivery;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryFailureReason {

    CUSTOMER_NOT_HOME("Müştəri ünvanda olmadı"),
    WRONG_ADDRESS("Ünvan tapılmadı"),
    CUSTOMER_UNREACHABLE("Müştəri ilə əlaqə qurulmadı"),
    CUSTOMER_REJECTED("Müştəri qəbul etmədi"),
    OTHER("Digər");

    private final String description;
}
