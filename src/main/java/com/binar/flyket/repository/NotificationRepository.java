package com.binar.flyket.repository;

import com.binar.flyket.dto.model.NotificationDTO;
import com.binar.flyket.model.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String> {

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.NotificationDTO(ntf.id, ntf.title, ntf.content, ntf.imgUrl, ntf.createdAt) " +
            "FROM Notification AS ntf " +
            "JOIN ntf.user " +
            "WHERE ntf.user.id = :user_id")
    Page<NotificationDTO> getNotificationByUserId(@Param("user_id") String userid, Pageable pageable);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.NotificationDTO(ntf.id, ntf.title, ntf.content, ntf.imgUrl, ntf.createdAt) " +
            "FROM Notification AS ntf " +
            "WHERE ntf.isRead = :is_read")
    List<NotificationDTO> countNotification(@Param("is_read") Boolean isRead);

    @Query(value = "SELECT NEW com.binar.flyket.dto.model.NotificationDTO(ntf.id, ntf.title, ntf.content, ntf.imgUrl, ntf.createdAt) " +
            "FROM Notification AS ntf " +
            "WHERE ntf.id = :notification_id")
    Optional<NotificationDTO> getNotificationDetail(@Param("notification_id") String notification_id);
}
