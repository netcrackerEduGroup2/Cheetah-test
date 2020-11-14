package com.ncedu.cheetahtest.security.config;

import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.developer.service.DeveloperService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class UserDetailsServiceImpl implements UserDetailsService {

    private DeveloperService developerService;

    @Autowired
    public UserDetailsServiceImpl(DeveloperService developerService) {
        this.developerService = developerService;
    }

    @Override
    public UserDetailsImpl loadUserByUsername(String email) throws UsernameNotFoundException {
        Developer developer = developerService.findDeveloperByEmail(email);
        return UserDetailsImpl.fromUserEntityToCustomUserDetails(developer);
    }
}
