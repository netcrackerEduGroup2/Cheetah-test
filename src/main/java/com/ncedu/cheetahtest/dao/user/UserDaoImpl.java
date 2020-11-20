package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.dao.resettoken.ResetTokenRowMapper;
import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.ResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;

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

    @Override
    public void editUser(User user) {
        String sql = "UPDATE users SET email = ?, password = ?, name = ?, role = ? where id = ?";

        jdbcTemplate.update(sql, user.getEmail(),
            user.getPass(),
            user.getName(),
            user.getRole(),
            user.getId());
    }

    @Override
    public void doActive(User user) {
        String sql = "UPDATE users SET status = ? where id = ?";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Boolean>) preparedStatement -> {
            preparedStatement.setString(1, "active");
            preparedStatement.setInt(2, user.getId());

            return preparedStatement.execute();
        });
    }

    @Override
    public void doInactive(User user) {
        String sql = "UPDATE users SET status = ? where id = ?";

        jdbcTemplate.execute(sql, (PreparedStatementCallback<Boolean>) preparedStatement -> {
            preparedStatement.setString(1, "inactive");
            preparedStatement.setInt(2, user.getId());

            return preparedStatement.execute();
        });
    }


}
