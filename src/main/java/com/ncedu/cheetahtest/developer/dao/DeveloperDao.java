package com.ncedu.cheetahtest.developer.dao;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.entity.ResetToken;

import java.util.List;

public interface DeveloperDao {

    List<Developer> getDevelopers();

    Developer findDeveloperByEmail(String email);

    void saveToken(ResetToken myToken);
}
