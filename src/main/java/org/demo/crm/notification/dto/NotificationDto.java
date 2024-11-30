package org.demo.crm.notification.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificationDto {

    private Long notificationId;
    private String message;
    private LocalDateTime dateSent;
    private String type;
    private Boolean readStatus;
}
