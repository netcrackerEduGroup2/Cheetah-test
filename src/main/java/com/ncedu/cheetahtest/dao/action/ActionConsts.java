package com.ncedu.cheetahtest.dao.action;

public class ActionConsts {

    public static final String SELECT_ALL =
            "SELECT id,title , description , id_compound , id_test_scenario, status FROM actions";

    public static final String SELECT_ACTIVE_ACTIONS_BY_TITLE =
            "SELECT actions.id, actions.title , actions.description , actions.id_compound , actions.id_test_scenario, " +
            "actions.status FROM actions INNER JOIN lib_act_compound ON actions.id = lib_act_compound.id_action " +
            "INNER JOIN library ON lib_act_compound.id_library = library.id " +
            "WHERE actions.title LIKE CONCAT('%',?,'%') AND library.id = ? AND actions.status ='active';";

    public static final String GET_INACTIVE_ACTIONS_BY_TITLE =
            "SELECT actions.id, actions.title , actions.description , actions.id_compound , actions.id_test_scenario, " +
            "actions.status FROM actions INNER JOIN lib_act_compound ON actions.id = lib_act_compound.id_action " +
            "INNER JOIN library ON lib_act_compound.id_library = library.id " +
            "WHERE actions.title LIKE CONCAT('%',?,'%') AND library.id = ? AND actions.status = 'inactive';";

    public static final String CREATE_ACTION =
            "INSERT INTO actions (title , description , id_compound , id_test_scenario, status) " +
            "VALUES (?, ?, ?, ?, ?::action_status);";

    public static final String SELECT_CURRVAL_ACTION_ID =
            "SELECT CURRVAL('actions_id_seq') ;";

    public static final String FIND_ACTION_BY_ID =
            "SELECT id, title , description , id_compound , id_test_scenario, status " +
            "FROM actions " +
            "WHERE id = ?;";

    public static final String FIND_ACTIONS_BY_ID_COMPOUND =
            "SELECT id, title , description , id_compound , id_test_scenario, status " +
            "FROM actions " +
            "WHERE id_compound = ?;";

    public static final String FIND_ACTIONS_BY_ID_TESTSCENARIO =
            "SELECT id, title , description , id_compound , id_test_scenario, status " +
            "FROM actions " +
            "WHERE id_test_scenario = ?;";

    public static final String EDIT_ACTION =
            "UPDATE actions SET title=?,description=?,id_compound=?,id_test_scenario=?,status=?::action_status " +
            "WHERE id = ?;";

    public static final String SET_TITLE =
            "UPDATE actions SET title = ? WHERE id = ?;";

    public static final String SET_DESCRIPTION =
            "UPDATE actions SET description = ? WHERE id = ?;";

    public static final String SET_COMPOUND_ID =
            "UPDATE actions SET idCompound = ? WHERE id = ?;";

    public static final String SET_TESTSCENARIO_ID =
            "UPDATE actions SET idTestScenario = ? WHERE id = ?;";

    public static final String SET_STATUS =
            "UPDATE actions SET status = ?::action_status WHERE id = ?;";

    public static final String REMOVE_ACTION_BY_ID =
            "DELETE FROM actions WHERE id = ?;";

}
