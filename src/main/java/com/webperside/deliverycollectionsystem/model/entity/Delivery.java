package com.webperside.deliverycollectionsystem.model.entity;

import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryStatus;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Hansı shipment üçün delivery aparılır
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    private Shipment shipment;

    /**
     * Çatdırmanı edən courier
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", nullable = false)
    private User courier;

    /**
     * Delivery status
     */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStatus status;

    /**
     * Courier çatdırılmaya nə vaxt başladı
     */
    @Column(name = "started_at")
    private LocalDateTime startedAt;

    /**
     * Uğurlu təhvil vaxtı
     */
    @Column(name = "delivered_at")
    private LocalDateTime deliveredAt;

    /**
     * Çatdırılma uğursuz olduqda səbəb
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "failure_reason")
    private DeliveryFailureReason failureReason;

    /**
     * Yenidən çatdırılma vaxtı
     */
    @Column(name = "rescheduled_at")
    private LocalDateTime rescheduledAt;

    /**
     * Müştəri qəbul edən şəxsin adı
     */
    @Column(name = "receiver_name")
    private String receiverName;

    /**
     * Təhvil zamanı qeyd
     */
    @Column(name = "notes")
    private String notes;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @Column(name = "is_deleted")
    private boolean isDeleted = false;

}
