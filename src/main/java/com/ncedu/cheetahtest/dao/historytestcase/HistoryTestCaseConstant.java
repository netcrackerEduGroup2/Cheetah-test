package com.ncedu.cheetahtest.dao.historytestcase;

public class HistoryTestCaseConstant {
    public static final String ADD_HISTORY_TEST_CASE =
            "INSERT INTO history_test_case " +
                    " (result, date_completed, id_test_case) " +
                    "  VALUES (?::test_case_result, ?, ?);";

    public static final String HISTORY_TEST_CASE_FAILED_COMPLETED_PAGINATION =
            "SELECT h.id AS h_id, h.result AS h_result, h.date_completed AS " +
                    "  h_date_completed, t.title AS t_title " +
                    " FROM history_test_case AS h INNER JOIN test_case t ON h.id_test_case = t.id " +
                    " WHERE h.result::text LIKE 'FAILED' OR h.result::text LIKE 'COMPLETE' " +
                    " LIMIT ? OFFSET ?; ";

    public static final String COUNT_TEST_CASE_FAILED_COMPLETED =
            "SELECT count(*) " +
            "   FROM history_test_case " +
            "   WHERE result::text LIKE 'FAILED' OR result::text LIKE 'COMPLETE'; ";

    public static final String EDIT_HISTORY_TEST_CASE_RESULT =
            "UPDATE history_test_case " +
                    " SET result = ? " +
                    " WHERE id = ?; ";
    public static final String GET_BY_ID = "SELECT id, result, date_completed, id_test_case FROM history_test_case WHERE id = ?";
}
