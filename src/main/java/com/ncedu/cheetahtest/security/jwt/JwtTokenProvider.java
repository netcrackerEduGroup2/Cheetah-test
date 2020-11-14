package com.ncedu.cheetahtest.security.jwt;

import com.ncedu.cheetahtest.developer.entity.Developer;

import javax.servlet.http.HttpServletRequest;


public interface JwtTokenProvider {
    String createToken(Developer developer);
    String getEmail(String token);
    boolean isTokenValid(String token);
}
