package com.webperside.deliverycollectionsystem.services.pricing_rules;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.entity.PricingRule;
import com.webperside.deliverycollectionsystem.model.enums.pricing_rule.PricingRuleStatus;
import com.webperside.deliverycollectionsystem.model.payload.pricing_rules.PricingRulesPayload;
import com.webperside.deliverycollectionsystem.model.payload.pricing_rules.PricingRulesRequest;
import com.webperside.deliverycollectionsystem.model.response.pricing_rules.PricingRuleResponse;
import com.webperside.deliverycollectionsystem.repository.PricingRuleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class PricingRuleServiceImpl implements PricingRuleService{

    private final PricingRuleRepository pricingRuleRepository;

    @Override
    public BigDecimal calculatePrice(PricingRulesRequest request) {
        PricingRule rule = pricingRuleRepository
                .findMatchingRule(
                        request.getWeight(),
                        request.getDistance(),
                        request.getServiceType()
                )
                .orElseThrow(() -> BaseException.notFound(PricingRule.class.getSimpleName(), "request", request));

        return rule.getBasePrice()
                .add(rule.getPricePerKm().multiply(request.getDistance()));
    }

    @Override
    public List<PricingRuleResponse> getAll() {

        List<PricingRule> pricingRules = pricingRuleRepository.findAllByIsDeletedFalse();
        List<PricingRuleResponse> pricingRuleResponses = new ArrayList<>();
        for (PricingRule pricingRule : pricingRules) {
            PricingRuleResponse pricingRuleResponse = fromEntityToResponse(pricingRule);
            pricingRuleResponses.add(pricingRuleResponse);
        }

        return pricingRuleResponses;
    }

    @Override
    public Long addPricingRule(PricingRulesPayload payload) {
        PricingRule pricingRule = fromPayloadToEntity(payload);
        return pricingRuleRepository.save(pricingRule).getId();
    }

    @Override
    public Long updatePricingRuleById(PricingRulesPayload payload, Long id) {

        if(!(pricingRuleRepository.findById(id).isPresent())){
            throw BaseException.notFound(PricingRule.class.getSimpleName(), "id", id);
        }

        PricingRule pricingRule = fromPayloadToEntity(payload);
        pricingRule.setId(id);
        pricingRuleRepository.save(pricingRule);

        return pricingRule.getId();
    }

    @Override
    public Long changeStatus(PricingRuleStatus status, Long id) {

        PricingRule pricingRule = pricingRuleRepository.findById(id).orElseThrow(()->BaseException.notFound(PricingRule.class.getSimpleName(), "id", id));
        pricingRule.setStatus(status);
        return pricingRuleRepository.save(pricingRule).getId();
    }

    @Override
    public Long softDeletePricingRuleById(Long id) {

        PricingRule pricingRule = pricingRuleRepository.findById(id).orElseThrow(()->BaseException.notFound(PricingRule.class.getSimpleName(), "id", id));
        pricingRule.setDeleted(true);
        return pricingRuleRepository.save(pricingRule).getId();
    }

    @Override
    public Long hardDeletePricingRuleById(Long id) {
        if(!(pricingRuleRepository.findById(id).isPresent())){
            throw BaseException.notFound(PricingRule.class.getSimpleName(), "id", id);
        }
        pricingRuleRepository.deleteById(id);
        return id;
    }

    private PricingRuleResponse fromEntityToResponse(PricingRule pricingRule){
        PricingRuleResponse pricingRuleResponse = new PricingRuleResponse();
        pricingRuleResponse.setId(pricingRule.getId());
        pricingRuleResponse.setMinWeight(pricingRule.getMinWeight());
        pricingRuleResponse.setMaxWeight(pricingRule.getMaxWeight());
        pricingRuleResponse.setMinDistance(pricingRule.getMinDistance());
        pricingRuleResponse.setMaxDistance(pricingRule.getMaxDistance());
        pricingRuleResponse.setServiceType(pricingRule.getServiceType());
        pricingRuleResponse.setPricePerKm(pricingRule.getPricePerKm());
        pricingRuleResponse.setBasePrice(pricingRule.getBasePrice());
        pricingRuleResponse.setStatus(pricingRule.getStatus());
        return pricingRuleResponse;
    }

    private PricingRule fromPayloadToEntity(PricingRulesPayload payload){
        PricingRule pricingRule = new PricingRule();
        pricingRule.setMinWeight(payload.getMinWeight());
        pricingRule.setMaxWeight(payload.getMaxWeight());
        pricingRule.setMinDistance(payload.getMinDistance());
        pricingRule.setMaxDistance(payload.getMaxDistance());
        pricingRule.setServiceType(payload.getServiceType());
        pricingRule.setPricePerKm(payload.getPricePerKm());
        pricingRule.setBasePrice(payload.getBasePrice());
        pricingRule.setStatus(payload.getStatus());
        return pricingRule;
    }

}
