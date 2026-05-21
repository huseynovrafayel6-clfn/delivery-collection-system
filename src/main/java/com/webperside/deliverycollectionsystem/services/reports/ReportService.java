package com.webperside.deliverycollectionsystem.services.reports;

import com.webperside.deliverycollectionsystem.model.dto.CourierPerformanceProjection;
import com.webperside.deliverycollectionsystem.model.response.reports.deliveries.DeliveryReportResponse;
import com.webperside.deliverycollectionsystem.model.response.reports.failures.FailureReportResponse;
import com.webperside.deliverycollectionsystem.model.response.reports.returns.ReturnReportResponse;

import java.util.List;

public interface ReportService {

    DeliveryReportResponse getDeliveryReport();

    FailureReportResponse getFailureReport();

    ReturnReportResponse getReturnReport();

    List<CourierPerformanceProjection> getCourierPerformanceReport();

}
