package com.ncedu.cheetahtest.service.user;

import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.user.ResetToken;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
public class UserServiceImpl implements UserService {

    private UserDao userDao;

    @Autowired
    public UserServiceImpl(UserDao userDao) {
        this.userDao = userDao;
    }

    @Override
    @Transactional
    public User findUserByEmail(String email) {
        return userDao.findUserByEmail(email);
    }

    @Override
    @Transactional
    public void createPasswordResetTokenForUser(User user, String token) {
        ResetToken myToken = new ResetToken(token, user.getId(), new Date());

        ResetToken resetToken = userDao.findResetTokenByUserId(user.getId());

        if (resetToken == null) {
            userDao.createToken(myToken);

            log.info("Token has been successfully created: " + myToken);
        } else {
            userDao.saveToken(myToken);

            log.info("Token has been successfully updated: " + myToken);
        }
    }

    @Override
    @Transactional
    public ResetToken findByToken(String token) {
        return userDao.findResetTokenByToken(token);
    }

    @Override
    @Transactional
    public void makeTokenExpired(ResetToken resetToken) {
        userDao.makeTokenExpired(resetToken);
    }

}
