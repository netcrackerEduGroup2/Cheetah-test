package com.ncedu.cheetahtest.security.service;

import com.ncedu.cheetahtest.developer.dao.DeveloperDao;
import com.ncedu.cheetahtest.developer.entity.Developer;
import com.ncedu.cheetahtest.security.entity.AccessTokenDto;
import com.ncedu.cheetahtest.security.entity.LoginDto;
import com.ncedu.cheetahtest.security.entity.RegisterDto;
import com.ncedu.cheetahtest.security.exceptions.BadCredentialsException;
import com.ncedu.cheetahtest.security.exceptions.UserAlreadyExistsException;
import com.ncedu.cheetahtest.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{

    private DeveloperDao developerDao;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(DeveloperDao developerDao, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.developerDao = developerDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Value("${db.developers.salt}")
    private String passwordSalt;

    @Override
    @Transactional
    public void register(RegisterDto registerDto) {

        if (developerDao.findDeveloperByEmail(registerDto.getEmail()) != null) {
            throw new UserAlreadyExistsException();
        }

        String passwordWithSalt = registerDto.getPassword() + passwordSalt;

        Developer newDeveloper = new Developer();

        newDeveloper.setEmail(registerDto.getEmail());
        newDeveloper.setName(registerDto.getName());
        newDeveloper.setPass(passwordEncoder.encode(passwordWithSalt));
        newDeveloper.setStatus("active");
        newDeveloper.setRole(registerDto.getRole());

        developerDao.createDeveloper(newDeveloper);
    }

    @Override
    public AccessTokenDto login(LoginDto loginDto) {

        Developer developer = developerDao.findDeveloperByEmail(loginDto.getEmail());

        if (developer == null) {
            throw new BadCredentialsException();
        }

        String loginDtoPasswordWithSalt = loginDto.getPassword() + passwordSalt;

        if (!passwordEncoder.matches(loginDtoPasswordWithSalt, developer.getPass())) {
            throw new BadCredentialsException();
        }
        
        String token = jwtTokenProvider.createToken(developer);
        return new AccessTokenDto(token);
    }
}
