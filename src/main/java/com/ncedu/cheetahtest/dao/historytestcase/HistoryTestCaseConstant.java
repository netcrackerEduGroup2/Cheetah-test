package com.ncedu.cheetahtest.dao.historytestcase;

public class HistoryTestCaseConstant {
    public static final String ADD_HISTORY_TEST_CASE =
            "INSERT INTO history_test_case " +
                    " (result, date_completed, id_test_case) " +
                    "  VALUES (?::test_case_result, ?, ?);";

    public static final String HISTORY_TEST_CASE_FAILED_COMPLETED_PAGINATION =
            "SELECT id, result, date_completed, id_test_case " +
            "   FROM history_test_case " +
            "   WHERE result LIKE ('FAILED' OR 'COMPLETE') " +
            "   LIMIT ? OFFSET ?;";

    public static final String COUNT_TEST_CASE_FAILED_COMPLETED =
            "SELECT count(*) " +
            "   FROM history_test_case " +
            "   WHERE result LIKE ('FAILED' OR 'COMPLETE');";

    public static final String EDIT_HISTORY_TEST_CASE_RESULT =
            "UPDATE history_test_case " +
                    " SET result = ? " +
                    " WHERE id = ?;";
}
