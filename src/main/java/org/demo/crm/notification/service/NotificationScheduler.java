package org.demo.crm.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyReminders() {
//         notificationService.createNotification(userId, "Reminder message", "REMINDER");
    }
}
