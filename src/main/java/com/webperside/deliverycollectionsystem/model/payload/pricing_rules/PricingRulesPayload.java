package com.webperside.deliverycollectionsystem.model.payload.pricing_rules;

import com.webperside.deliverycollectionsystem.model.enums.pricing_rule.PricingRuleStatus;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PricingRulesPayload {

    BigDecimal minWeight;
    BigDecimal maxWeight;
    BigDecimal minDistance;
    BigDecimal maxDistance;
    ServiceType serviceType;
    BigDecimal pricePerKm;
    BigDecimal basePrice;
    PricingRuleStatus status;

}
