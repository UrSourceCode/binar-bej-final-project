package com.binar.flyket.dummy;

import com.binar.flyket.model.Notification;

import java.util.List;

public class NotificationDummies {

    public static List<Notification> notificationList() {
        Notification ntf1 = new Notification();
        ntf1.setId("ntf-1");
        ntf1.setContent("Content ntf1");
        ntf1.setTitle("Title ntf1");
        ntf1.setIsRead(false);
        ntf1.setImgUrl("");
        ntf1.setUser(UserDummies.users().get(1));

        Notification ntf2 = new Notification();
        ntf2.setId("ntf-2");
        ntf2.setContent("Content ntf2");
        ntf2.setTitle("Title ntf2");
        ntf2.setIsRead(false);
        ntf2.setImgUrl("");
        ntf2.setUser(UserDummies.users().get(1));

        return List.of(ntf1, ntf2);
    }

}
