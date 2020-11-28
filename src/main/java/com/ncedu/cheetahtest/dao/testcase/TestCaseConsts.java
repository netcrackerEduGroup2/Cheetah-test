package com.ncedu.cheetahtest.dao.testcase;

public final class TestCaseConsts {

    public static final String SELECT_ALL_PARAMS_FROM_TEST_CASE =
            "SELECT id, title, project_id, status, result " +
            "FROM test_case ";
    public static final String WHERE_STATUS_ACTIVE = "WHERE status = 'ACTIVE' ";
    public static final String UPDATE = "UPDATE test_case SET ";

    public static final String GET_ACTIVE_TEST_CASES_PAGINATED =
            SELECT_ALL_PARAMS_FROM_TEST_CASE +
            WHERE_STATUS_ACTIVE + " ORDER BY title LIMIT ? OFFSET ? ";

    public static final String GET_AMOUNT_OF_ACTIVE_TEST_CASES =
            "SELECT COUNT(id) FROM test_case " +
            WHERE_STATUS_ACTIVE;

    public static final String GET_ALL_TEST_CASES_PAGINATED =
            SELECT_ALL_PARAMS_FROM_TEST_CASE +
            " ORDER BY title LIMIT ? OFFSET ? ";

    public static final String GET_AMOUNT_OF_ALL_TEST_CASES = "SELECT COUNT(id) FROM test_case";

    public static final String UPDATE_TEST_CASE_SQL =
            UPDATE + " title = ?, project_id = ?," +
            " status = ?::test_case_status, result = ?::test_case_result" +
            " WHERE id = ?";

    public static final String FIND_TEST_CASE_BY_ID =
            SELECT_ALL_PARAMS_FROM_TEST_CASE +
            " WHERE id = ? LIMIT 1";

    public static final String FIND_TEST_CASE_BY_TITLE_EXCEPT_CURRENT =
            SELECT_ALL_PARAMS_FROM_TEST_CASE +
            " WHERE title = ? AND id <> ? LIMIT 1";

    public static final String DEACTIVATE_TEST_CASE_SQL =
            UPDATE + " status = 'INACTIVE'::test_case_status WHERE id = ?";

    public static final String FIND_ACTIVE_TEST_CASE_LIKE_TITLE =
                    SELECT_ALL_PARAMS_FROM_TEST_CASE +
                    WHERE_STATUS_ACTIVE +
                   " AND title LIKE ? " +
                   " ORDER BY title LIMIT ? OFFSET ?";

    public static final String GET_AMOUNT_OF_ACTIVE_SEARCHED_TEST_CASES =
                    " SELECT COUNT(id)" +
                    " FROM test_case " +
                    WHERE_STATUS_ACTIVE +
                    " AND title LIKE ? LIMIT 1";

    public static final String FIND_ALL_TEST_CASE_LIKE_TITLE =
                    SELECT_ALL_PARAMS_FROM_TEST_CASE +
                    " WHERE title LIKE ? " +
                    " ORDER BY title LIMIT ? OFFSET ?";

    public static final String GET_AMOUNT_OF_ALL_SEARCHED_TEST_CASES =
                    " SELECT COUNT(id)" +
                    " FROM test_case " +
                    " WHERE title LIKE ? LIMIT 1";

    public static final String CREATE_TEST_CASE_SQL =
                    "INSERT INTO test_case (title, project_id, status, result)" +
                    " VALUES (?,?,?::test_case_status,?::test_case_result)";

    public static final String GET_TEST_CASE_ID_BY_TITLE =
            "SELECT id FROM test_case WHERE title = ?";
}
