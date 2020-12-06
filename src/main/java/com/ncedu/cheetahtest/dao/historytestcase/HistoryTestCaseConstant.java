package com.ncedu.cheetahtest.dao.historytestcase;

public class HistoryTestCaseConstant {
    public static final String ADD_HISTORY_TEST_CASE =
            "INSERT INTO history_test_case " +
                    " (result, date_completed, id_test_case) " +
                    "  VALUES (?, ?, ?);";
}
