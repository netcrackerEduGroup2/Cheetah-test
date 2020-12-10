package com.ncedu.cheetahtest.entity.notification;

import lombok.Data;

import java.util.Date;

@Data
public class TestCaseNotification {
    private int id;
    private int userId;
    private NotificationStatus notificationStatus;
    private Date date;
    private int testCaseId;
    private int projectId;
    private ReadStatus readStatus;

    public TestCaseNotification() {
        this.date = new Date();
    }
}
