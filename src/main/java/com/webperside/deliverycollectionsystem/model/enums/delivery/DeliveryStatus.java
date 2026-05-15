package com.webperside.deliverycollectionsystem.model.enums.delivery;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DeliveryStatus {

    PENDING("Çatdırılma gözləyir"),
    IN_PROGRESS("Çatdırılma başladı"),
    DELIVERED("Uğurla çatdırıldı"),
    FAILED("Çatdırılma uğursuz oldu"),
    RESCHEDULED("Yenidən planlaşdırıldı");

    private final String description;
}
