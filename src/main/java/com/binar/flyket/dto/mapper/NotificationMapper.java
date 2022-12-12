package com.binar.flyket.dto.mapper;

import com.binar.flyket.dto.model.NotificationDTO;
import com.binar.flyket.model.Notification;

public class NotificationMapper {

    public static NotificationDTO toDto(Notification notification) {
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setContent(notification.getContent());
        notificationDTO.setTitle(notification.getTitle());
        notificationDTO.setImgUrl(notification.getImgUrl());
        notificationDTO.setId(notificationDTO.getId());
        notificationDTO.setCreatedAt(notification.getCreatedAt());
        return notificationDTO;
    }
}
