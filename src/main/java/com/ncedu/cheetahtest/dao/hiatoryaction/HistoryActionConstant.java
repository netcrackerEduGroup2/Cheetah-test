package com.ncedu.cheetahtest.dao.hiatoryaction;

public class HistoryActionConstant {
    public static final String ADD_HISTORY_ACTION =
            "INSERT INTO action_result " +
                    "   (result, screenshot_url, general_order, id_history_test_case, compound_id, action_element, argument, id_action) " +
                    "   VALUES (?, ? , ?, ?, ?, ?, ?, ?);";
    public static final String GET_HISTORY_ACTION_BY_TEST_HISTORY_ID =

            "SELECT id, compound_id, result, screenshot_url, general_order, id_history_test_case " +
                    " FROM action_result " +
                    " WHERE id_history_test_case = ? " +
                    " ORDER BY general_order;";

    public static final String GET_RUN_ACRIONS_BY_ID_TEST_CASE_HISTORY =
            "SELECT ar.id AS ar_id, ar.compound_id AS ar_compound_id, ar.result AS ar_result, " +
            "   ar.screenshot_url AS ar_screenshot_url, a.type AS a_type," +
            "   ar.action_element AS ar_action_element, ar.argument AS ar_argument" +
            " FROM action_result AS ar INNER JOIN action AS a ON ar.id_action = a.id " +
            " WHERE ar.id_history_test_case=?; ";

    public static final String GET_ALL_ID_HISTORY_TEST_CASE =
            "SELECT id FROM history_test_case LIMIT 10;";

}
