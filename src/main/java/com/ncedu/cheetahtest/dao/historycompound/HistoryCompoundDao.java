package com.ncedu.cheetahtest.dao.historycompound;

public interface HistoryCompoundDao {
    void addCompound(String actionType, String element, String argument,
                     int generalOrder,int idHistoryTestCase);
}
