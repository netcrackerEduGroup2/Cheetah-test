package com.ncedu.cheetahtest.developer.service;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.dao.DeveloperDao;
import com.ncedu.cheetahtest.developer.entity.ResetToken;
import com.ncedu.cheetahtest.mail.entity.PasswordDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private DeveloperDao developerDao;

    @Autowired
    public DeveloperServiceImpl(DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }

    @Override
    @Transactional
    public Developer findDeveloperByEmail(String email) {
        return developerDao.findDeveloperByEmail(email);
    }

    @Override
    @Transactional
    public void createPasswordResetTokenForUser(Developer developer, String token) {
        ResetToken myToken = new ResetToken(token, developer.getId(), new Date());
        developerDao.saveToken(myToken);
    }

    @Override
    @Transactional
    public ResetToken findByToken(String token) {
        return developerDao.findByToken(token);
    }

    @Override
    public void changeUserPassword(ResetToken resetToken, String password) {
        developerDao.changeUserPassword(resetToken, password);
    }

    @Override
    public boolean validatePassword(PasswordDTO passwordDTO, String token) {
        return developerDao.findDeveloperByToken(token).equals(passwordDTO.getPassword());
    }


}
