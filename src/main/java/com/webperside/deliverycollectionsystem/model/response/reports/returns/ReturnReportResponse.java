package com.webperside.deliverycollectionsystem.model.response.reports.returns;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class ReturnReportResponse {

    private long totalReturns;
    private long initiatedReturns;
    private long approvedReturns;
    private long inTransitReturns;
    private long completedReturns;
    private long cancelledReturns;
    private List<ReturnReasonStatistic> reasonStatistics;


}
