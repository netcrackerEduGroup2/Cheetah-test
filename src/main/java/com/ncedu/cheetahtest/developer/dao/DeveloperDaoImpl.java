package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;

@Repository
public class DeveloperDaoImpl implements DeveloperDao {


    private JdbcTemplate jdbcTemplate;

    @Autowired
    public DeveloperDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createDeveloper(Developer developer) {
        String sql = "INSERT INTO developer (email, password, name, role, status) VALUES (?,?,?,?::developer_role,?::developer_status)";

        jdbcTemplate.update(
                sql,
                developer.getEmail(),
                developer.getPass(),
                developer.getName(),
                developer.getRole(),
                developer.getStatus()
                );

    }

    @Override
    public Developer findDeveloperByEmail(String email) {
        String sql = "SELECT id, email, password, name, role, status, reset_token_id FROM developer WHERE email = ?";

        List<Developer> developers = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, email),
                new DeveloperRowMapper());

        if (developers.size() == 1) {
            return developers.get(0);
        }

        return null;
    }

    @Override
    public Developer findDeveloperByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, email, password, name, role, status, reset_token_id FROM developer WHERE email = ? AND password = ?";

        List<Developer> developers = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                },
                new DeveloperRowMapper());

        if (developers.size() == 1) {
            return developers.get(0);
        }

        return null;
    }

    @Override
    public void saveToken(ResetToken myToken) {

        String sql = "UPDATE reset_token SET token = ?, expiry_date = ? WHERE developer_id = ?";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Boolean>) preparedStatement -> {
            preparedStatement.setString(1, myToken.getToken());
            preparedStatement.setTimestamp(2, new Timestamp(new Date().getTime() + ResetToken.getEXPIRATION()));
            preparedStatement.setInt(3, myToken.getDeveloperId());

            return preparedStatement.execute();
        });
    }

    @Override
    public ResetToken findByToken(String token) {
        String sql = "SELECT id, token, expiry_date, developer_id FROM reset_token WHERE token = ?";

        List<ResetToken> resetTokens = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, token),
                new ResetTokenRowMapper());

        if (resetTokens.size() == 1) {
            return resetTokens.get(0);
        }

        return null;
    }

    @Override
    public void changeUserPassword(ResetToken resetToken, String password) {
        String sql = "UPDATE developer SET password = ? WHERE reset_token_id = ?";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Boolean>) preparedStatement -> {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, resetToken.getId());

            return preparedStatement.execute();
        });
    }

    @Override
    public String findDeveloperByToken(String token) {
        String sql = "SELECT password FROM developer WHERE reset_token_id IN (SELECT id FROM reset_token WHERE token = ?)";

        return jdbcTemplate.queryForObject(sql, new Object[]{token}, String.class);
    }

}
