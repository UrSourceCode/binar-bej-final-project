package com.binar.flyket.service;

import com.binar.flyket.dto.model.NotificationDTO;
import com.binar.flyket.dto.response.NotificationResponse;
import org.springframework.data.domain.Pageable;

public interface NotificationService {

    NotificationResponse getNotificationByUserId(String userId, Pageable pageable);
    NotificationDTO getNotificationDetail(String notificationId);

}
