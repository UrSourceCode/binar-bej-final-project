package com.binar.flyket.dto.response;

import com.binar.flyket.dto.model.NotificationDTO;
import com.binar.flyket.model.Notification;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class NotificationResponse {
    private String userId;
    private Integer unread;
    private Integer totalNotification;
    private List<NotificationDTO> notifications;
}
