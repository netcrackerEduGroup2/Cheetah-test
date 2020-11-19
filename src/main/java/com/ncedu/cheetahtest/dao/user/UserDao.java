package com.ncedu.cheetahtest.dao.user;

import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.ResetToken;

public interface UserDao {

    void createDeveloper(User user);

    User findUserByEmail(String email);

    User findUserByEmailAndPassword(String email, String password);

    void saveToken(ResetToken myToken);

    ResetToken findResetTokenByToken(String token);

    void createToken(ResetToken myToken);

    void makeTokenExpired(ResetToken resetToken);

    ResetToken findResetTokenByUserId(int id);

    void changeUserPassword(ResetToken resetToken, String password);

    User findUserByToken(String token);






}
