package com.webperside.deliverycollectionsystem.model.response.reports.failures;

import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class FailureReasonStatistic {

    private DeliveryFailureReason reason;
    private long count;

}
