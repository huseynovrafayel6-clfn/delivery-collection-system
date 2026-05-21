package com.webperside.deliverycollectionsystem.model.response.reports.failures;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
public class FailureReportResponse {

    private long totalFailures;

    private List<FailureReasonStatistic> reasons;

}
