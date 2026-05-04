package com.webperside.deliverycollectionsystem.model.entity;

import com.webperside.deliverycollectionsystem.model.enums.pricing_rule.PricingRuleStatus;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Entity
@Table(name = "pricing_rules")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PricingRule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    BigDecimal minWeight;
    BigDecimal maxWeight;
    BigDecimal minDistance;
    BigDecimal maxDistance;

    @Enumerated(EnumType.STRING)
    ServiceType serviceType;

    BigDecimal pricePerKm;
    BigDecimal basePrice;

    @Enumerated(EnumType.STRING)
    PricingRuleStatus status;

    boolean isDeleted = false;
}
