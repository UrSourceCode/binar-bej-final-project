package com.binar.flyket.service;

import com.binar.flyket.dto.mapper.NotificationMapper;
import com.binar.flyket.dto.model.NotificationDTO;
import com.binar.flyket.dto.response.NotificationResponse;
import com.binar.flyket.exception.ExceptionType;
import com.binar.flyket.exception.FlyketException;
import com.binar.flyket.model.Notification;
import com.binar.flyket.model.user.User;
import com.binar.flyket.repository.NotificationRepository;
import com.binar.flyket.repository.UserRepository;
import com.binar.flyket.utils.Constants;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationServiceImpl implements NotificationService {

    private NotificationRepository notificationRepository;
    private UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository,
                                   UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public NotificationResponse getNotificationByUserId(String userId, Pageable pageable) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        List<NotificationDTO> notifications = notificationRepository.getNotificationByUserId(userId, pageable).getContent();
        List<NotificationDTO> countNotification = notificationRepository.countNotification(false);
        NotificationResponse notificationResponse = new NotificationResponse();
        notificationResponse.setUserId(userId);
        notificationResponse.setTotalNotification(notifications.size());
        notificationResponse.setUnread(countNotification.size());
        notificationResponse.setNotifications(notifications);
        return notificationResponse;
    }

    @Override
    public NotificationDTO getNotificationDetail(String notificationId) {
        Optional<Notification> notification = notificationRepository.findById(notificationId);
        if(notification.isEmpty())
            throw FlyketException.throwException(ExceptionType.NOT_FOUND, HttpStatus.NOT_FOUND, Constants.NOT_FOUND_MSG);
        Notification notificationModel = notification.get();
        notificationModel.setIsRead(true);
        notificationRepository.save(notificationModel);
        return NotificationMapper.toDto(notificationModel);
    }
}
