package com.webperside.deliverycollectionsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.webperside.deliverycollectionsystem.model.enums.shipments.DeliveryType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "shipments")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Shipment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "sender_id", nullable = false)
    User sender;

    String receiverName;
    String receiverEmail;
    String receiverPhoneNumber;
    String deliveryAddress;
    BigDecimal weight;
    String dimensions;

    @Enumerated(EnumType.STRING)
    ServiceType serviceType;

    @Enumerated(EnumType.STRING)
    DeliveryType deliveryType;

    String notes;

    @Enumerated(EnumType.STRING)
    ShipmentStatus status;

    boolean isDeleted=false;

    @CreationTimestamp
    @Column(updatable = false)
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @Column(unique = true, nullable = false, updatable = false)
    String trackingNumber;

    @PrePersist
    public void generateTrackingNumber() {
        if (trackingNumber == null) {
            trackingNumber = UUID.randomUUID().toString();
        }
    }
}
