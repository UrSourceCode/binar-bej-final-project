package com.binar.flyket.service;

import com.binar.flyket.dto.model.NotificationDTO;
import com.binar.flyket.dummy.NotificationDummies;
import com.binar.flyket.dummy.UserDummies;
import com.binar.flyket.model.Notification;
import com.binar.flyket.repository.NotificationRepository;
import com.binar.flyket.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

class NotificationServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private NotificationRepository notificationRepository;

    @InjectMocks
    private NotificationServiceImpl notificationService;

    @BeforeEach
    void init() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testGetNotificationByUserId() {
        String userId = UserDummies.users().get(1).getId();

        List<NotificationDTO> ntfDto = NotificationDummies.notificationList()
                        .stream()
                        .map(it -> {
                            NotificationDTO dto = new NotificationDTO();
                            dto.setCreatedAt(LocalDateTime.now());
                            dto.setId(it.getId());
                            dto.setTitle(it.getTitle());
                            dto.setImgUrl(it.getImgUrl());
                            dto.setContent(it.getContent());
                            return dto;
                        }).toList();

        Pageable pageable = PageRequest.of(0, 10, Sort.by("createdAt").descending());

        PageImpl<NotificationDTO> page = new PageImpl<>(ntfDto);

        Mockito.when(userRepository.findById(userId))
                .thenReturn(Optional.of(UserDummies.users().get(0)));
        Mockito.when(notificationRepository.countNotification(false, userId))
                .thenReturn(ntfDto);
        Mockito.when(notificationRepository.getNotificationByUserId(userId, pageable))
                .thenReturn(page);

        var actualValue  = notificationService.getNotificationByUserId(userId, pageable);
        var expectedUserId = "Buyer-1";
        var expectedTotalNotification = 2;

        Assertions.assertEquals(expectedUserId, actualValue.getUserId());
        Assertions.assertEquals(expectedTotalNotification, actualValue.getTotalNotification());
        Assertions.assertEquals(ntfDto.size(), actualValue.getNotifications().size());
        Assertions.assertEquals(ntfDto.get(0).getTitle(), actualValue.getNotifications().get(0).getTitle());
        Assertions.assertEquals(ntfDto.get(0).getContent(), actualValue.getNotifications().get(0).getContent());
        Assertions.assertEquals(ntfDto.get(0).getId(), actualValue.getNotifications().get(0).getId());
        Assertions.assertEquals(ntfDto.get(1).getTitle(), actualValue.getNotifications().get(1).getTitle());
        Assertions.assertEquals(ntfDto.get(1).getContent(), actualValue.getNotifications().get(1).getContent());
        Assertions.assertEquals(ntfDto.get(1).getId(), actualValue.getNotifications().get(1).getId());
    }

    @Test
    void testGetNotificationDetail() {
        String ntfId = "ntf-1";

        Notification ntfModel = NotificationDummies.notificationList().get(0);

        Mockito.when(notificationRepository.findById(ntfId))
                .thenReturn(Optional.of(NotificationDummies.notificationList().get(0)));

        var actualValue = notificationService.getNotificationDetail(ntfId);
        var expectedContent = ntfModel.getContent();
        var expectedId = ntfModel.getId();
        var expectedTitle = ntfModel.getTitle();

        Assertions.assertEquals(expectedContent, actualValue.getContent());
        Assertions.assertEquals(expectedTitle, actualValue.getTitle());
    }


}