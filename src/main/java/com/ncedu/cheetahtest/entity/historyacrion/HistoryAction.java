package com.ncedu.cheetahtest.entity.historyacrion;

import lombok.Data;

@Data
public class HistoryAction {
    private int id;
    private int compoundId;
    private String result;
    private String screenshotURL;
    private int generalOrder;
    private int idHistoryTestCase;
}
