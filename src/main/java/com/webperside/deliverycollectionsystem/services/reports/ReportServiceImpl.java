package com.webperside.deliverycollectionsystem.services.reports;

import com.webperside.deliverycollectionsystem.model.dto.CourierPerformanceProjection;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryStatus;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnReason;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnStatus;
import com.webperside.deliverycollectionsystem.model.response.reports.deliveries.DeliveryReportResponse;
import com.webperside.deliverycollectionsystem.model.response.reports.failures.FailureReasonStatistic;
import com.webperside.deliverycollectionsystem.model.response.reports.failures.FailureReportResponse;
import com.webperside.deliverycollectionsystem.model.response.reports.returns.ReturnReasonStatistic;
import com.webperside.deliverycollectionsystem.model.response.reports.returns.ReturnReportResponse;
import com.webperside.deliverycollectionsystem.repository.AssignmentRepository;
import com.webperside.deliverycollectionsystem.repository.DeliveryRepository;
import com.webperside.deliverycollectionsystem.repository.ReportRepository;
import com.webperside.deliverycollectionsystem.repository.ReturnRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReportServiceImpl implements ReportService {

    private final DeliveryRepository deliveryRepository;
    private final ReturnRepository returnRepository;
    private final ReportRepository reportRepository;

    @Override
    public DeliveryReportResponse getDeliveryReport() {
        return DeliveryReportResponse.builder()
                .totalDeliveries(deliveryRepository.count())
                .pendingCount(
                        deliveryRepository.countByStatus(DeliveryStatus.PENDING)
                )
                .inProgressCount(
                        deliveryRepository.countByStatus(DeliveryStatus.IN_PROGRESS)
                )
                .deliveredCount(
                        deliveryRepository.countByStatus(DeliveryStatus.DELIVERED)
                )
                .failedCount(
                        deliveryRepository.countByStatus(DeliveryStatus.FAILED)
                )
                .rescheduledCount(
                        deliveryRepository.countByStatus(DeliveryStatus.RESCHEDULED)
                )

                .build();
    }

    @Override
    public FailureReportResponse getFailureReport() {

        List<FailureReasonStatistic> failureReasonStatistics = new ArrayList<>();
        failureReasonStatistics.add(
                FailureReasonStatistic.builder()
                        .reason(DeliveryFailureReason.CUSTOMER_NOT_HOME)
                        .count(deliveryRepository.countByFailureReason(DeliveryFailureReason.CUSTOMER_NOT_HOME))
                        .build()
        );
        failureReasonStatistics.add(
                FailureReasonStatistic.builder()
                        .reason(DeliveryFailureReason.WRONG_ADDRESS)
                        .count(deliveryRepository.countByFailureReason(DeliveryFailureReason.WRONG_ADDRESS))
                        .build()
        );
        failureReasonStatistics.add(
                FailureReasonStatistic.builder()
                        .reason(DeliveryFailureReason.CUSTOMER_UNREACHABLE)
                        .count(deliveryRepository.countByFailureReason(DeliveryFailureReason.CUSTOMER_UNREACHABLE))
                        .build()
        );
        failureReasonStatistics.add(
                FailureReasonStatistic.builder()
                        .reason(DeliveryFailureReason.CUSTOMER_REJECTED)
                        .count(deliveryRepository.countByFailureReason(DeliveryFailureReason.CUSTOMER_REJECTED))
                        .build()
        );
        failureReasonStatistics.add(
                FailureReasonStatistic.builder()
                        .reason(DeliveryFailureReason.OTHER)
                        .count(deliveryRepository.countByFailureReason(DeliveryFailureReason.OTHER))
                        .build()
        );

        return FailureReportResponse.builder()
                .totalFailures(
                        deliveryRepository.countByStatus(DeliveryStatus.FAILED)
                )
                .reasons(
                        failureReasonStatistics
                )
                .build();
    }

    @Override
    public ReturnReportResponse getReturnReport() {

        List<ReturnReasonStatistic> returnReasonStatistics = new ArrayList<>();
        returnReasonStatistics.add(
                ReturnReasonStatistic.builder()
                        .reason(
                                ReturnReason.CUSTOMER_REJECTED
                        )
                        .count(returnRepository.countByReason(ReturnReason.CUSTOMER_REJECTED))
                        .build()
        );
        returnReasonStatistics.add(
                ReturnReasonStatistic.builder()
                        .reason(
                                ReturnReason.DELIVERY_FAILED
                        )
                        .count(returnRepository.countByReason(ReturnReason.DELIVERY_FAILED))
                        .build()
        );
        returnReasonStatistics.add(
                ReturnReasonStatistic.builder()
                        .reason(
                                ReturnReason.WRONG_ADDRESS
                        )
                        .count(returnRepository.countByReason(ReturnReason.WRONG_ADDRESS))
                        .build()
        );
        returnReasonStatistics.add(
                ReturnReasonStatistic.builder()
                        .reason(
                                ReturnReason.CUSTOMER_UNREACHABLE
                        )
                        .count(returnRepository.countByReason(ReturnReason.CUSTOMER_UNREACHABLE))
                        .build()
        );
        returnReasonStatistics.add(
                ReturnReasonStatistic.builder()
                        .reason(
                                ReturnReason.DAMAGED_PACKAGE
                        )
                        .count(returnRepository.countByReason(ReturnReason.DAMAGED_PACKAGE))
                        .build()
        );
        returnReasonStatistics.add(
                ReturnReasonStatistic.builder()
                        .reason(
                                ReturnReason.OTHER
                        )
                        .count(returnRepository.countByReason(ReturnReason.OTHER))
                        .build()
        );

        return ReturnReportResponse.builder()
                .totalReturns(returnRepository.count())
                .initiatedReturns(returnRepository.countByStatus(ReturnStatus.INITIATED))
                .approvedReturns(returnRepository.countByStatus(ReturnStatus.APPROVED))
                .inTransitReturns(returnRepository.countByStatus(ReturnStatus.IN_TRANSIT))
                .completedReturns(returnRepository.countByStatus(ReturnStatus.COMPLETED))
                .cancelledReturns(returnRepository.countByStatus(ReturnStatus.CANCELLED))
                .reasonStatistics(returnReasonStatistics)
                .build();
    }

    @Override
    public List<CourierPerformanceProjection> getCourierPerformanceReport() {
        return reportRepository.getCourierPerformance();
    }
}
