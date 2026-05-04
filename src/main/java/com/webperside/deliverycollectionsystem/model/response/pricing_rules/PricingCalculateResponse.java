package com.webperside.deliverycollectionsystem.model.response.pricing_rules;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PricingCalculateResponse {

    BigDecimal totalPrice;

}
