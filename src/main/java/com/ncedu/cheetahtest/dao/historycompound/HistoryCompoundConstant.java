package com.ncedu.cheetahtest.dao.historycompound;

public class HistoryCompoundConstant {
    public static final String ADD_HISTORY_COMPOUND =
            "INSERT INTO compound_result " +
                    "   (action_type, element, argument, general_order,id_history_test_case)" +
                    "   VALUES (?, ?, ?, ?, ?);";
}
