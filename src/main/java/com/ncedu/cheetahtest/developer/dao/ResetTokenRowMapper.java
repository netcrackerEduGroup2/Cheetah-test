package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.ResetToken;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.Date;

public class ResetTokenRowMapper implements RowMapper<ResetToken> {
    public static final String ID = "id";
    public static final String TOKEN = "token";
    public static final String EXPIRY_DATE = "expiry_date";
    public static final String ID_DEVELOPER = "id_developer";

    @Override
    public ResetToken mapRow(ResultSet rs, int i) throws SQLException {
        ResetToken resetToken = new ResetToken();

        resetToken.setId(rs.getInt(ID));
        resetToken.setToken(rs.getString(TOKEN));
        resetToken.setDeveloperId(rs.getInt(ID_DEVELOPER));
        resetToken.setExpiryDate(rs.getTimestamp(EXPIRY_DATE));

        return resetToken;
    }
}
