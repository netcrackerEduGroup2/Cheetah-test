package com.ncedu.cheetahtest.dao.hiatoryaction;

public interface HistoryActionDao {
    void addAction(String result, String screenshotURL,
                   int generalOrder, int idHistoryTestCase);
}
