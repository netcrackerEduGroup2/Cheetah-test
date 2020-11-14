package com.ncedu.cheetahtest.security.service;

import com.ncedu.cheetahtest.security.entity.AccessTokenDto;
import com.ncedu.cheetahtest.security.entity.LoginDto;
import com.ncedu.cheetahtest.security.entity.RegisterDto;

public interface AuthService {
    void register(RegisterDto registerDto);
    AccessTokenDto login(LoginDto loginDto);
}
