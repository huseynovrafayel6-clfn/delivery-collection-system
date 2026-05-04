package com.webperside.deliverycollectionsystem.services.pricing_rules;

import com.webperside.deliverycollectionsystem.model.enums.pricing_rule.PricingRuleStatus;
import com.webperside.deliverycollectionsystem.model.payload.pricing_rules.PricingRulesRequest;
import com.webperside.deliverycollectionsystem.model.payload.pricing_rules.PricingRulesPayload;
import com.webperside.deliverycollectionsystem.model.response.pricing_rules.PricingRuleResponse;

import java.math.BigDecimal;
import java.util.List;

public interface PricingRuleService {

    BigDecimal calculatePrice(PricingRulesRequest request);

    List<PricingRuleResponse> getAll();

    Long addPricingRule(PricingRulesPayload payload);

    Long updatePricingRuleById(PricingRulesPayload payload, Long id);

    Long changeStatus(PricingRuleStatus status, Long id);

    Long softDeletePricingRuleById(Long id);

    Long hardDeletePricingRuleById(Long id);

}
