package com.webperside.deliverycollectionsystem.model.response.reports.returns;

import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnReason;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class ReturnReasonStatistic {

    private ReturnReason reason;
    private long count;
}
