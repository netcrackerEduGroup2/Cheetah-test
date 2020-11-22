package com.ncedu.cheetahtest.service.user;

import com.ncedu.cheetahtest.dao.resettoken.ResetTokenDao;
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
    private ResetTokenDao resetTokenDao;

    @Autowired
    public UserServiceImpl(UserDao userDao, ResetTokenDao resetTokenDao) {
        this.userDao = userDao;
        this.resetTokenDao = resetTokenDao;
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

        ResetToken resetToken = resetTokenDao.findResetTokenByUserId(user.getId());

        if (resetToken == null) {
            resetTokenDao.createToken(myToken);

            log.info("Token has been successfully created: " + myToken);
        } else {
            resetTokenDao.saveToken(myToken);

            log.info("Token has been successfully updated: " + myToken);
        }
    }

    @Override
    @Transactional
    public ResetToken findByToken(String token) {
        return resetTokenDao.findResetTokenByToken(token);
    }

    @Override
    @Transactional
    public void makeTokenExpired(ResetToken resetToken) {
        resetTokenDao.makeTokenExpired(resetToken);
    }

    @Override
    @Transactional
    public boolean editUser(User user) {
        if(user.getRole().equals("admin")) {
            userDao.editUser(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean doActive(User user) {
        if(user.getRole().equals("admin")) {
            userDao.doActive(user);
            return true;
        }
        return false;
    }

    @Override
    @Transactional
    public boolean doInactive(User user) {
        if(user.getRole().equals("admin")) {
            userDao.doInactive(user);
            return true;
        }
        return false;
    }

    @Override
    public User findUserById(long id) {
        return userDao.findUserById(id);
    }

}
