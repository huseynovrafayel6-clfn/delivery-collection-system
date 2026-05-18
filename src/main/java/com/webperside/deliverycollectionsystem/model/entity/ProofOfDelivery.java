package com.webperside.deliverycollectionsystem.model.entity;

import com.webperside.deliverycollectionsystem.model.enums.proof_of_deliveries.VerificationType;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "proof_of_deliveries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProofOfDelivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false, unique = true)
    private Shipment shipment;

    @Column(name = "receiver_name", nullable = false)
    private String receiverName;

    @Column(name = "receiver_phone")
    private String receiverPhone;

    @Column(name = "received_at", nullable = false)
    private LocalDateTime receivedAt;

    @Column(name = "courier_note", columnDefinition = "TEXT")
    private String courierNote;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "otp_id", unique = true)
    private Otp otp;

    @Column(name = "signature_url")
    private String signatureUrl;

    @Column(name = "photo_url")
    private String photoUrl;

    @Column(name = "identity_number")
    private String identityNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "verification_type")
    private VerificationType verificationType;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;
}
