package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.entity.PricingRule;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@Repository
public interface PricingRuleRepository extends JpaRepository<PricingRule, Long> {

    @Query("""
    SELECT r FROM PricingRule r
    WHERE r.status = 'ACTIVE'
      AND :weight BETWEEN r.minWeight AND r.maxWeight
      AND :distance BETWEEN r.minDistance AND r.maxDistance
      AND r.serviceType = :serviceType
""")
    Optional<PricingRule> findMatchingRule(
            BigDecimal weight,
            BigDecimal distance,
            ServiceType serviceType
    );

    List<PricingRule> findAllByIsDeletedFalse();
}
