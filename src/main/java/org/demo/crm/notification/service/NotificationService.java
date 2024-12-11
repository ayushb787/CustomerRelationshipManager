package org.demo.crm.notification.service;

import org.demo.crm.notification.dto.NotificationDto;
import org.demo.crm.notification.exception.NotificationNotFoundException;
import org.demo.crm.notification.model.Notification;
import org.demo.crm.notification.repository.NotificationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    public List<NotificationDto> getUserNotifications(Long userId) {
        return notificationRepository.findByUserId(userId)
                .stream()
                .filter(notification -> !notification.getReadStatus())
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    public NotificationDto markAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
                .orElseThrow(() -> new NotificationNotFoundException("Notification not found"));
        notification.setReadStatus(true);
        return convertToDto(notificationRepository.save(notification));
    }

    public Notification createNotification(Long userId, String message, String type) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setDateSent(LocalDateTime.now());
        notification.setType(type);
        notification.setReadStatus(false);
        return notificationRepository.save(notification);
    }

    private NotificationDto convertToDto(Notification notification) {
        NotificationDto dto = new NotificationDto();
        dto.setNotificationId(notification.getNotificationId());
        dto.setMessage(notification.getMessage());
        dto.setDateSent(notification.getDateSent());
        dto.setType(notification.getType());
        dto.setReadStatus(notification.getReadStatus());
        return dto;
    }
}
