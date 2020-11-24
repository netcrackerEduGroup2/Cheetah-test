package com.ncedu.cheetahtest.dao.compound;

public class CompoundConsts {

    public static final String SELECT_ALL =
            "SELECT id, title, description, id_test_scenario, status FROM compounds;";

    public static final String SELECT_ACTIVE_COMPOUND_BY_TITLE =
            "SELECT compounds.id, compounds.title, compounds.description, compounds.id_test_scenario, " +
            "compounds.status FROM compounds INNER JOIN lib_act_compound ON compounds.id = lib_act_compound.id_compound " +
            "INNER JOIN library ON lib_act_compound.id_library = library.id " +
            "WHERE compounds.title LIKE CONCAT('%', ?, '%') AND library.id = ? AND compounds.status = 'active';";

    public static final String SELECT_INACTIVE_COMPOUND_BY_TITLE =
            "SELECT compounds.id, compounds.title, compounds.description, compounds.id_test_scenario, " +
            "compounds.status FROM compounds INNER JOIN lib_act_compound ON compounds.id = lib_act_compound.id_compound " +
            "INNER JOIN library ON lib_act_compound.id_library = library.id " +
            "WHERE compounds.title LIKE CONCAT('%', ?, '%') AND library.id = ? AND compounds.status='inactive';";

    public static final String CREATE_COMPOUND =
            "INSERT INTO compounds (title,description,id_test_scenario,status) " +
            "VALUES (?,?,?,?::action_status);";

    public static final String SELECT_CURRVAL_COMPOUNDS_ID =
            "SELECT CURRVAL('compounds_id_seq');";

    public static final String FIND_COMPOUND_BY_ID =
            "SELECT id,title,description,id_test_scenario,status " +
            "FROM compounds WHERE id = ?;";

    public static final String FIND_COMPOUND_BY_ID_TESTSCENARIO =
            "SELECT id,title,description,id_test_scenario,status " +
            "FROM compounds " +
            "WHERE id_test_scenario = ?;";

    public static final String EDIT_COMPOUND =
            "UPDATE compounds SET title = ?, description = ?, id_test_scenario = ?, status = ?::compound_status " +
            "WHERE compounds.id = ?;";

    public static final String SET_TITLE =
            "UPDATE compounds SET title = ? WHERE id = ?;";

    public static final String SET_DESCRIPTION =
            "UPDATE compounds SET description = ? WHERE id = ?;";

    public static final String SET_TESTSCENARIO_ID =
            "UPDATE compounds SET id_test_scenario = ? WHERE id = ?;";

    public static final String SET_STATUS =
            "UPDATE compounds SET status = ? WHERE id = ?;";

    public static final String REMOVE_COMPOUND_BY_ID =
            "DELETE FROM compounds WHERE id = ?;";

}
