package com.webperside.deliverycollectionsystem.model.entity;

import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tracking_events")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackingEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    Shipment shipment;

    @Enumerated(EnumType.STRING)
    ShipmentStatus status;

    String description;

    @CreationTimestamp
    LocalDateTime createdAt;
}
