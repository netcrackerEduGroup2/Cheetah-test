package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;

public interface DeveloperDao {

    void createDeveloper(Developer developer);

    Developer findDeveloperByEmail(String email);

    Developer findDeveloperByEmailAndPassword(String email, String password);

    void saveToken(ResetToken myToken);

    ResetToken findResetTokenByToken(String token);

    void changeUserPassword(ResetToken resetToken, String password);

    Developer findDeveloperByToken(String token);

    ResetToken findResetTokenByDeveloperId(int id);

    void createToken(ResetToken myToken);
}
