package org.demo.crm.notification.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "notifications")
public class Notification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long notificationId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private String message;

    @Column(nullable = false)
    private LocalDateTime dateSent;

    @Column(nullable = false)
    private String type; // e.g., "REMINDER", "NEW_LEAD"

    @Column(nullable = false)
    private Boolean readStatus = false;
}
