package com.webperside.deliverycollectionsystem.model.payload.pricing_rules;

import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PricingRulesRequest {

    BigDecimal weight;
    BigDecimal distance;
    ServiceType serviceType;

}
