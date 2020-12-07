package com.ncedu.cheetahtest.dao.hiatoryaction;

public class HistoryActionConstant {
    public static final String ADD_HISTORY_ACTION =
            "INSERT INTO action_result " +
                    "   (result, screenshot_url, general_order, id_history_test_case, compound_id)" +
                    "   VALUES (?, ?, ?, ?, ?);";

    public static final String GET_HISTORY_ACTION_BY_TEST_HISTORY_ID =
            "SELECT id, compound_id, result, screenshot_url, general_order, id_history_test_case " +
                    " FROM action_result " +
                    " WHERE id_history_test_case = ?; ";
}
