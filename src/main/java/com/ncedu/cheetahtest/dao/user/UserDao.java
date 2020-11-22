package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.ResetToken;
import java.util.Date;

public interface UserDao {

    void createDeveloper(User user);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    void changeUserPassword(ResetToken resetToken, String password);

    User findUserByToken(String token);

    void setUserLastRequest(String email, Date lastRequest);

    void editUser(User user);

    void doActive(User user);

    void doInactive(User user);

    User findUserById(long id);
}
