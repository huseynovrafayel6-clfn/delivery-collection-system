package com.webperside.deliverycollectionsystem.controller;

import com.webperside.deliverycollectionsystem.model.dto.CourierPerformanceProjection;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.reports.deliveries.DeliveryReportResponse;
import com.webperside.deliverycollectionsystem.model.response.reports.failures.FailureReportResponse;
import com.webperside.deliverycollectionsystem.model.response.reports.returns.ReturnReportResponse;
import com.webperside.deliverycollectionsystem.services.reports.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/reports")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @GetMapping("/deliveries")
    public BaseResponse<DeliveryReportResponse> getDeliveryReports(){
        return BaseResponse.success(reportService.getDeliveryReport());
    }

    @GetMapping("/failures")
    public BaseResponse<FailureReportResponse> getFailureReports(){
        return BaseResponse.success(reportService.getFailureReport());
    }

    @GetMapping("/returns")
    public BaseResponse<ReturnReportResponse> getReturnReports(){
        return BaseResponse.success(reportService.getReturnReport());
    }

    @GetMapping("/couriers/performance")
    public BaseResponse<List<CourierPerformanceProjection>> getCourierPerformanceReports(){
        return BaseResponse.success(reportService.getCourierPerformanceReport());
    }

}
