package com.webperside.deliverycollectionsystem.model.response.pricing_rules;

import com.webperside.deliverycollectionsystem.model.enums.pricing_rule.PricingRuleStatus;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PricingRuleResponse {

    Long id;
    BigDecimal minWeight;
    BigDecimal maxWeight;
    BigDecimal minDistance;
    BigDecimal maxDistance;
    ServiceType serviceType;
    BigDecimal pricePerKm;
    BigDecimal basePrice;
    PricingRuleStatus status;

}
