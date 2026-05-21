package com.webperside.deliverycollectionsystem.model.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentStatus;
import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "assignments")
@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Assignment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "shipment_id", nullable = false)
    Shipment shipment;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "courier_id", nullable = false)
    User courier;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    AssignmentStatus status;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type", nullable = false)
    AssignmentType assignmentType;

    @CreationTimestamp
    @Column(name = "assigned_at", nullable = false)
    LocalDateTime assignedAt;

    @UpdateTimestamp
    @Column(name = "updated_at")
    LocalDateTime updatedAt;

    @Column(name = "is_deleted", nullable = false)
    boolean isDeleted = false;

}
