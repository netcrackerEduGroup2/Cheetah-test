package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;

public interface DeveloperDao {

    void createDeveloper(Developer developer);

    Developer findDeveloperByEmail(String email);

    Developer findDeveloperByEmailAndPassword(String email, String password);

    void saveToken(ResetToken myToken);

    void changeUserPassword(ResetToken resetToken, String password);

    ResetToken findResetTokenByToken(String token);

    Developer findDeveloperByToken(String token);

    ResetToken findResetTokenByDeveloperId(int id);

    void createToken(ResetToken myToken);

    void makeTokenExpired(ResetToken resetToken);
}
