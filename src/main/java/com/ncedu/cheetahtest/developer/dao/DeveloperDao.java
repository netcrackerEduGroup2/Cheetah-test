package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;

public interface DeveloperDao {

    Developer findDeveloperByEmail(String email);

    void saveToken(ResetToken myToken);

    ResetToken findByToken(String token);

    void changeUserPassword(ResetToken resetToken, String password);

    String findDeveloperByToken(String token);
}
