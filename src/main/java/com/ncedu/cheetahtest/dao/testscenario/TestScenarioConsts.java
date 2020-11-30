package com.ncedu.cheetahtest.dao.testscenario;

public class TestScenarioConsts {

    private TestScenarioConsts() {
    }

    public static final String CREATE_TEST_SCENARIO = "INSERT INTO test_scenario(title, description, status, test_case_id) VALUES (?,?,?,?)";

    public static final String EDIT_TEST_SCENARIO = "UPDATE test_scenario SET title = ? , description = ? , status = ? , test_case_id = ? " +
            "WHERE id = ?";
    public static final String DELETE_TEST_SCENARIO = "DELETE FROM test_scenario WHERE id = ?";

    public static final String GET_TOTAL_ELEMENTS_FROM_SEARCH = "SELECT count(*) FROM test_scenario WHERE test_case_id = ? AND title LIKE concat('%',?,'%')";

    public static final String FIND_BY_TITLE = "SELECT id,title,description,status,test_case_id FROM test_scenario " +
            "WHERE title = ?";

    public static final String FIND_BY_TITLE_LIKE = "SELECT id,title,description,status,test_case_id " +
            "FROM test_scenario WHERE title LIKE CONCAT('%',?,'%') AND test_case_id=? " +
            "ORDER BY title limit ? offset ?";

    public static final String DEACTIVATE_TEST_SCENARIO = "UPDATE test_scenario SET status = 'INACTIVE'::test_scenario_status WHERE id = ?";

    public static final String FIND_SCENARIOS_FROM_TEST_CASE =
            "SELECT id,title,description,status,test_case_id FROM test_scenario" +
                    " WHERE test_case_id=? ORDER BY title limit ? offset ?";

    public static final String GET_SCENARIOS_FROM_TEST_CASE_AMOUNT = "SELECT count(*) FROM test_scenario " +
            "WHERE test_case_id = ? ";

    public static final String GET_ALL_TEST_SCENARIOS_AMOUNT = "SELECT count(*) FROM test_scenario";

    public static final String FIND_TEST_SCENARIO_BY_TITLE_EXCEPT_CURRENT =
            "SELECT id,title,description,status,test_case_id FROM test_scenario " +
                    " WHERE title = ? AND id <> ? LIMIT 1";

}
