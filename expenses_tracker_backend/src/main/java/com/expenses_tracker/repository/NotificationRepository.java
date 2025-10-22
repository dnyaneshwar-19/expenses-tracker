package com.expenses_tracker.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.expenses_tracker.entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    /**
     * Find all notifications for a specific user
     */
    List<Notification> findByUserId(Long userId);

    /**
     * Find all unread notifications for a specific user
     */
    List<Notification> findByUserIdAndIsReadFalse(Long userId);

    /**
     * Find all read notifications for a specific user
     */
    List<Notification> findByUserIdAndIsReadTrue(Long userId);
}
