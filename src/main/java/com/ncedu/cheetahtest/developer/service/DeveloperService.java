package com.ncedu.cheetahtest.developer.service;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;
import com.ncedu.cheetahtest.mail.entity.PasswordDTO;

public interface DeveloperService {

    Developer findDeveloperByEmail(String email);

    void createPasswordResetTokenForUser(Developer developer, String token);

    ResetToken findByToken(String token);

    void makeTokenExpired(ResetToken resetToken);
}
