package com.ncedu.cheetahtest.service.user;

import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.ResetToken;

public interface UserService {

    User findUserByEmail(String email);

    void createPasswordResetTokenForUser(User user, String token);

    ResetToken findByToken(String token);

    void makeTokenExpired(ResetToken resetToken);
}