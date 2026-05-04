package com.webperside.deliverycollectionsystem.controller;

import com.webperside.deliverycollectionsystem.model.enums.pricing_rule.PricingRuleStatus;
import com.webperside.deliverycollectionsystem.model.payload.pricing_rules.PricingRulesPayload;
import com.webperside.deliverycollectionsystem.model.payload.pricing_rules.PricingRulesRequest;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.pricing_rules.PricingRuleResponse;
import com.webperside.deliverycollectionsystem.services.pricing_rules.PricingRuleService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;

@RestController
@RequestMapping("/pricing-rules")
@RequiredArgsConstructor
public class PricingRuleController {

    private final PricingRuleService pricingRuleService;

    @PostMapping("/calculate")
    public BaseResponse<BigDecimal> calculatePricingRule(@RequestBody PricingRulesRequest request) {
        return BaseResponse.success(pricingRuleService.calculatePrice(request));
    }

    @GetMapping("/get-all")
    public BaseResponse<List<PricingRuleResponse>> getAllPricingRules() {
        return BaseResponse.success(pricingRuleService.getAll());
    }

    @PostMapping("/add")
    public BaseResponse<Long> addPricingRule(@RequestBody PricingRulesPayload payload) {
        return BaseResponse.success(pricingRuleService.addPricingRule(payload));
    }

    @PostMapping("/update/{id}")
    public BaseResponse<Long> updatePricingRule(@RequestBody PricingRulesPayload payload,
                                                @PathVariable Long id) {
        return BaseResponse.success(pricingRuleService.updatePricingRuleById(payload, id));
    }

    // todo: status ucun ayroi request class custom validation
    @PatchMapping("/{id}/status")
    public BaseResponse<Long> updateStatus(@PathVariable Long id,
                                           @RequestBody PricingRuleStatus status) {
        return BaseResponse.success(pricingRuleService.changeStatus(status, id));
    }

    @DeleteMapping("/soft/delete/{id}")
    public BaseResponse<Long> softDeleteById(@PathVariable Long id) {
        return BaseResponse.success(pricingRuleService.softDeletePricingRuleById(id));
    }

    @DeleteMapping("/hard/delete/{id}")
    public BaseResponse<Long> hardDeleteById(@PathVariable Long id) {
        return BaseResponse.success(pricingRuleService.hardDeletePricingRuleById(id));
    }
}
