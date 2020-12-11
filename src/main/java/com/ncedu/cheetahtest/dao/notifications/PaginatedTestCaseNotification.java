package com.ncedu.cheetahtest.dao.notifications;

import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import lombok.Data;

import java.util.List;
@Data
public class PaginatedTestCaseNotification {
    List<TestCaseNotification> testCaseNotifications;
    int totalElements;
}
