package com.webperside.deliverycollectionsystem.model.payload.proof_of_deliveries;

import com.webperside.deliverycollectionsystem.model.enums.proof_of_deliveries.VerificationType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ProofOfDeliveryPayload {

    Long shipmentId;
    String receiverName;
    String receiverPhone;
    LocalDateTime receivedAt;
    String courierNote;
//    Boolean otpVerified;
//    String otpCode;
//    String signatureUrl;
//    String photoUrl;
    String identityNumber;
    VerificationType verificationType;

}
