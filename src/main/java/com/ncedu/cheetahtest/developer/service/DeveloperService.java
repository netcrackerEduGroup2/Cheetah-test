package com.ncedu.cheetahtest.developer.service;

import com.ncedu.cheetahtest.developer.entity.Developer;

import java.util.List;


public interface DeveloperService {

    List<Developer> getDevelopers();

    Developer findDeveloperByEmail(String email);

    void createPasswordResetTokenForUser(Developer developer, String token);
}
