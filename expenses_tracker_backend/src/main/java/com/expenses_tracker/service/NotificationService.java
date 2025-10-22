package com.expenses_tracker.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.expenses_tracker.entity.Notification;
import com.expenses_tracker.entity.RecurringBill;
import com.expenses_tracker.entity.User;
import com.expenses_tracker.repository.NotificationRepository;
import com.expenses_tracker.repository.RecurringBillRepository;
import com.expenses_tracker.repository.UserRepository;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    @Autowired
    private RecurringBillRepository recurringBillRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * Scheduled task that runs once per day at 9:00 AM
     * Checks for bills due in the next 2-3 days and creates notifications
     */
    @Scheduled(cron = "0 0 9 * * *") // Runs daily at 9:00 AM
    public void checkForUpcomingBills() {
        LocalDate today = LocalDate.now();
        
        // Check for bills due in the next 2-3 days
        for (int daysAhead = 2; daysAhead <= 3; daysAhead++) {
            LocalDate targetDate = today.plusDays(daysAhead);
            int dayOfMonth = targetDate.getDayOfMonth();
            
            // Find all recurring bills due on this day of the month
            List<RecurringBill> billsDue = recurringBillRepository.findByDayOfMonthDue(dayOfMonth);
            
            for (RecurringBill bill : billsDue) {
                // Create notification for the user
                String message = String.format("Your '%s' bill of ₹%.2f is due in %d days.", 
                    bill.getName(), bill.getAmount(), daysAhead);
                
                createNotification(bill.getUser(), message);
            }
        }
    }

    /**
     * Create a new notification for a user
     */
    public Notification createNotification(User user, String message) {
        Notification notification = new Notification(message, user);
        return notificationRepository.save(notification);
    }

    /**
     * Create a new notification for a user by user ID
     */
    public Notification createNotification(Long userId, String message) {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new RuntimeException("User not found with id: " + userId));
        return createNotification(user, message);
    }

    /**
     * Get all notifications for a user
     */
    public List<Notification> getNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserId(userId);
    }

    /**
     * Get all unread notifications for a user
     */
    public List<Notification> getUnreadNotificationsByUserId(Long userId) {
        return notificationRepository.findByUserIdAndIsReadFalse(userId);
    }

    /**
     * Mark a notification as read
     */
    public void markNotificationAsRead(Long notificationId) {
        Notification notification = notificationRepository.findById(notificationId)
            .orElseThrow(() -> new RuntimeException("Notification not found with id: " + notificationId));
        notification.setRead(true);
        notificationRepository.save(notification);
    }

    /**
     * Mark all notifications as read for a user
     */
    public void markAllNotificationsAsRead(Long userId) {
        List<Notification> unreadNotifications = notificationRepository.findByUserIdAndIsReadFalse(userId);
        for (Notification notification : unreadNotifications) {
            notification.setRead(true);
        }
        notificationRepository.saveAll(unreadNotifications);
    }
}
