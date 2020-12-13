package com.ncedu.cheetahtest.dao.notifications;

import lombok.Data;

import java.util.List;

@Data
public class NotificationStatusChangeDTO {
    private List<Integer> notifications;
}
