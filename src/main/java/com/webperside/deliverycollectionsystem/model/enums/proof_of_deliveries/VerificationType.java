package com.webperside.deliverycollectionsystem.model.enums.proof_of_deliveries;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum VerificationType {

    OTP("OTP təsdiqi"),
    SIGNATURE("İmza ilə təsdiq"),
    PHOTO("Şəkil ilə təsdiq"),
    ID_CARD("Şəxsiyyət vəsiqəsi ilə təsdiq"),
    MANUAL("Manual təsdiq");

    private final String description;
}