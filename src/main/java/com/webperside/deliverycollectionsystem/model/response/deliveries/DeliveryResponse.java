package com.webperside.deliverycollectionsystem.model.response.deliveries;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class DeliveryResponse {

    Long id;
    Long shipmentId;
    Long courierId;
    DeliveryStatus status;
    LocalDateTime startedAt;
    LocalDateTime deliveredAt;
    DeliveryFailureReason failureReason;
    LocalDateTime rescheduledAt;
    String receiverName;
    String notes;

}
