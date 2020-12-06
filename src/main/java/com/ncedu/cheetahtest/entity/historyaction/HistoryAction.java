package com.ncedu.cheetahtest.entity.historyaction;

import lombok.Data;

@Data
public class HistoryAction {
    private int id;
    private String actionType;
    private String screenshotURL;
    private int generalOrder;
    private int idHistoryTestCase;
}
