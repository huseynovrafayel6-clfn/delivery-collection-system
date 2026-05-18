package com.webperside.deliverycollectionsystem.model.enums.returns;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReturnStatus {

    INITIATED("Geri qaytarma başladıldı"),
    APPROVED("Geri qaytarma təsdiqləndi"),
    IN_TRANSIT("Göndərənə geri göndərilir"),
    COMPLETED("Geri qaytarma tamamlandı"),
    CANCELLED("Geri qaytarma ləğv edildi");

    private final String description;

}
