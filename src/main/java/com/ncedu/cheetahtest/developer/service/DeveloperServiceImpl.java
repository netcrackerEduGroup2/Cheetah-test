package com.ncedu.cheetahtest.developer.service;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.dao.DeveloperDao;
import com.ncedu.cheetahtest.developer.entity.ResetToken;
import com.ncedu.cheetahtest.mail.entity.PasswordDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@Slf4j
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

        ResetToken resetToken = developerDao.findResetTokenByDeveloperId(developer.getId());

        if (resetToken == null) {
            developerDao.createToken(myToken);

            log.info("Token has been successfully created: " + myToken);
        } else {
            developerDao.saveToken(myToken);

            log.info("Token has been successfully updated: " + myToken);
        }
    }

    @Override
    @Transactional
    public ResetToken findByToken(String token) {
        return developerDao.findResetTokenByToken(token);
    }

}
