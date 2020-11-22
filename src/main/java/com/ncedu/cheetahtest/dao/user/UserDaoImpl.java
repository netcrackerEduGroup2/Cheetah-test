package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.ResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDaoImpl implements UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public UserDaoImpl(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Override
    public void createDeveloper(User user) {
        String sql = "INSERT INTO users (email, password, name, role, status) VALUES (?,?,?,?::user_role,?::user_status)";

        jdbcTemplate.update(
                sql,
                user.getEmail(),
                user.getPass(),
                user.getName(),
                user.getRole(),
                user.getStatus()
        );

    }

    @Override
    public User findUserByEmail(String email) {
        String sql = "SELECT id, email, password, name, role, status, reset_token_id FROM users WHERE email = ?";

        List<User> users = jdbcTemplate.query(
                sql,
                preparedStatement -> preparedStatement.setString(1, email),
                new UserRowMapper());

        if (users.size() == 1) {
            return users.get(0);
        }

        return null;
    }

    @Override
    public User findUserByEmailAndPassword(String email, String password) {
        String sql = "SELECT id, email, password, name, role, status, reset_token_id FROM users WHERE email = ? AND password = ?";

        List<User> users = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, email);
                    preparedStatement.setString(2, password);
                },
                new UserRowMapper());

        if (users.size() == 1) {
            return users.get(0);
        }

        return null;
    }

    @Override
    public void changeUserPassword(ResetToken resetToken, String password) {
        String sql = "UPDATE users SET password = ? WHERE id = ?";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Boolean>) preparedStatement -> {
            preparedStatement.setString(1, password);
            preparedStatement.setInt(2, resetToken.getDeveloperId());

            return preparedStatement.execute();
        });
    }

    @Override
    public User findUserByToken(String token) {
        String sql = "SELECT id, email, password, name, role, status, reset_token_id FROM users WHERE id IN (SELECT user_id FROM reset_token WHERE token = ?)";

        List<User> users = jdbcTemplate.query(
                sql,
                preparedStatement -> {
                    preparedStatement.setString(1, token);
                },
                new UserRowMapper()
        );

        if (users.size() == 1) {
            return users.get(0);
        }

        return null;
    }


}
