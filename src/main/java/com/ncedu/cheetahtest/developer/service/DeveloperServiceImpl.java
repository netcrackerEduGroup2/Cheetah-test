package com.ncedu.cheetahtest.developer.service;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.dao.DeveloperDao;
import com.ncedu.cheetahtest.developer.entity.ResetToken;
import com.ncedu.cheetahtest.mail.model.PasswordResetToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class DeveloperServiceImpl implements DeveloperService {

    private DeveloperDao developerDao;

    @Autowired
    public DeveloperServiceImpl(DeveloperDao developerDao) {
        this.developerDao = developerDao;
    }

    @Override
    @Transactional
    public List<Developer> getDevelopers() {
        return developerDao.getDevelopers();
    }

    @Override
    @Transactional
    public Developer findDeveloperByEmail(String email) {
        return developerDao.findDeveloperByEmail(email);
    }

    @Override
    @Transactional
    public void createPasswordResetTokenForUser(Developer developer, String token) {
        ResetToken myToken = new ResetToken(token, developer);
        developerDao.saveToken(myToken);
    }
}
