package com.webperside.deliverycollectionsystem.model.response.reports.deliveries;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class DeliveryReportResponse {

    private long totalDeliveries;
    private long pendingCount;
    private long inProgressCount;
    private long deliveredCount;
    private long failedCount;
    private long rescheduledCount;

}
