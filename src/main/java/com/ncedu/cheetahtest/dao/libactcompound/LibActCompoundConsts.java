package com.ncedu.cheetahtest.dao.libactcompound;

public class LibActCompoundConsts {

    public static final String CREATE_LIBACTCOMPOUND =
            "INSERT INTO lib_act_compound (id_library , id_compound , id_action) " +
            "VALUES (?, ?, ?);";

    public static final String FIND_LIBACTCOMPOUNDS_BY_ID =
            "SELECT id, id_library , id_compound , id_action FROM lib_act_compound " +
            "WHERE id_library = ?;";

    public static final String FIND_LIBACTCOMPOUNDS_BY_ID_COMPOUND =
            "SELECT id, id_library , id_compound , id_action FROM lib_act_compound " +
            "WHERE id_compound = ?;";

    public static final String FIND_LIBACTCOMPOUNDS_BY_ID_ACT =
            "SELECT id, id_library , id_compound , id_action FROM lib_act_compound " +
            "WHERE id_action = ?;";

    public static final String SET_ID_COMPOUND =
            "UPDATE lib_act_compound SET id_compound = ? WHERE id = ?;";

    public static final String SET_ID_ACTION =
            "UPDATE lib_act_compound SET id_action = ? WHERE id = ?;";

    public static final String REMOVE_BY_LIBRARY_ID =
            "DELETE from lib_act_compound WHERE id_library = ?;";

    public static final String REMOVE_BY_COMPOUND_ID =
            "DELETE from lib_act_compound WHERE id_compound = ?;";

    public static final String REMOVE_BY_ACTION_ID =
            "DELETE from lib_act_compound WHERE id_action = ?;";

}
