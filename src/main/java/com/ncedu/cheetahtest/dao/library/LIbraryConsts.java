package com.ncedu.cheetahtest.dao.library;

public class LIbraryConsts {

    public static final String CREATE_LIBRARY =
            "INSERT INTO library (description, name , create_date) VALUES (?,?,?);";

    public static final String SELECT_ALL =
            "SELECT id, description, name , create_date FROM library;";

    public static final String FIND_LIBRARY_BY_ID =
            "SELECT id, description, name , create_date FROM library WHERE id = ?;";

    public static final String SET_DESCRIPTION =
            "UPDATE library SET description = ? WHERE id = ?;";

    public static final String REMOVE_LIBRARY =
            "DELETE FROM library WHERE id = ?;";

    public static final String SELECT_LIBRARIES_BY_NAME =
            "SELECT id, description, name , create_date FROM library WHERE name LIKE CONCAT('%',?,'%');";

    public static final String SET_NAME =
            "UPDATE library SET name = ? WHERE id = ?;";

}
