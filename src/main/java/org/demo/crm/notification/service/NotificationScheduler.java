package org.demo.crm.notification.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class NotificationScheduler {

    @Autowired
    private NotificationService notificationService;

    // Schedule reminders for follow-ups every day at 8 AM
    @Scheduled(cron = "0 0 8 * * ?")
    public void sendDailyReminders() {
        // Yet to be done - Logic to fetch leads/customers requiring reminders and send notifications
//         notificationService.createNotification(userId, "Reminder message", "REMINDER");
    }
}
