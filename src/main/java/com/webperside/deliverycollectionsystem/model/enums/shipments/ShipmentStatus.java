package com.webperside.deliverycollectionsystem.model.enums.shipments;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ShipmentStatus {

    CREATED("Göndəriş yaradıldı"),
    REGISTERED("Göndəriş sistemdə qeydiyyata alındı"),
    PRICE_CALCULATED("Çatdırılma qiyməti hesablandı"),
    ACCEPTED("Göndəriş qəbul edildi"),
    PREPARED("Göndəriş çatdırılma üçün hazırlandı"),
    ASSIGNED_TO_COURIER("Göndəriş kuryerə təyin edildi"),
    PICKED_UP_BY_COURIER("Göndəriş kuryer tərəfindən götürüldü"),
    IN_TRANSIT("Göndəriş yoldadır"),
    OUT_FOR_DELIVERY("Göndəriş çatdırılma üçün ünvana aparılır"),
    DELIVERY_ATTEMPT_FAILED("Çatdırılma cəhdi uğursuz oldu"),
    DELIVERED("Göndəriş uğurla çatdırıldı"),
    RETURN_REQUESTED("Geri qaytarılma tələbi yaradıldı"),
    RETURN_IN_PROGRESS("Göndəriş geri qaytarılır"),
    RETURNED("Göndəriş geri qaytarıldı"),
    CANCELLED("Göndəriş ləğv edildi"),
    DAMAGED("Göndəriş zədələndi");

    private final String description;
}
