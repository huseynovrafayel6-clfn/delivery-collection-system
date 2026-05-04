package com.webperside.deliverycollectionsystem.model.enums.shipments;

public enum ShipmentStatus {

    CREATED,
    REGISTERED,
    PRICE_CALCULATED,
    ACCEPTED,
    PREPARED,
    ASSIGNED_TO_COURIER,
    PICKED_UP_BY_COURIER,
    IN_TRANSIT,
    OUT_FOR_DELIVERY,
    DELIVERY_ATTEMPT_FAILED,
    DELIVERED,
    RETURN_REQUESTED,
    RETURN_IN_PROGRESS,
    RETURNED,
    CANCELLED,
    DAMAGED

}
