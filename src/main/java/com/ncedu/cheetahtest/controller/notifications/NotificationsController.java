package com.ncedu.cheetahtest.controller.notifications;

import com.ncedu.cheetahtest.dao.notifications.NotificationsDao;
import com.ncedu.cheetahtest.dao.notifications.PaginatedTestCaseNotification;
import com.ncedu.cheetahtest.entity.notification.NotificationResponse;
import com.ncedu.cheetahtest.entity.notification.TestCaseNotification;
import com.ncedu.cheetahtest.service.notifications.TestCaseNotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@CrossOrigin(origins = "${frontend.ulr}")
@RequestMapping("/api/notifications")
public class NotificationsController {
    private TestCaseNotificationService testCaseNotificationService;
    @Autowired
    public NotificationsController(TestCaseNotificationService testCaseNotificationService) {
        this.testCaseNotificationService = testCaseNotificationService;
    }

    @PutMapping("/{id}")
    TestCaseNotification editNotification(@PathVariable int id,
                                          @RequestBody TestCaseNotification testCaseNotification){
        return testCaseNotificationService.editNotification(testCaseNotification,id);
    }
    @GetMapping("")
    PaginatedTestCaseNotification getNotificationsByUserID(@RequestParam("idUser") int idUser,
                                                              @RequestParam("size") int size,
                                                              @RequestParam ("page") int page){
        return testCaseNotificationService.getNotificationsByUserIdPaginated(idUser,size,page);
    }
    @GetMapping("/all")
    List<TestCaseNotification> getAllNotificationsByUserID(@RequestParam("idUser") int idUser){
        return testCaseNotificationService.getAllNotificationsByUserId(idUser);
    }
    @DeleteMapping
    NotificationResponse deleteNotification(@RequestParam("id") int id){
        testCaseNotificationService.deleteNotification(id);
        return new NotificationResponse("Sucess");
    }


}
