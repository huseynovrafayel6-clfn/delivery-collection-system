package com.webperside.deliverycollectionsystem.model.dto;

public record CourierPerformanceProjection (

    Long courierId,

    String courierName,

    Long totalAssignments,

    Long completedDeliveries,

    Long failedDeliveries
) {
    }
