package com.ncedu.cheetahtest.service.security;

import com.ncedu.cheetahtest.dao.user.UserDao;
import com.ncedu.cheetahtest.entity.user.User;
import com.ncedu.cheetahtest.entity.user.ResetToken;
import com.ncedu.cheetahtest.entity.mail.PasswordDTO;
import com.ncedu.cheetahtest.entity.security.AccessTokenDto;
import com.ncedu.cheetahtest.entity.security.LoginDto;
import com.ncedu.cheetahtest.entity.security.RegisterDto;
import com.ncedu.cheetahtest.exception.security.BadCredentialsException;
import com.ncedu.cheetahtest.exception.security.UserAlreadyExistsException;
import com.ncedu.cheetahtest.config.security.jwt.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AuthServiceImpl implements AuthService{

    private UserDao userDao;

    private PasswordEncoder passwordEncoder;

    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    public AuthServiceImpl(UserDao userDao, PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider) {
        this.userDao = userDao;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Value("${db.users.salt}")
    private String passwordSalt;

    @Override
    @Transactional
    public void register(RegisterDto registerDto) {

        if (userDao.findUserByEmail(registerDto.getEmail()) != null) {
            throw new UserAlreadyExistsException();
        }

        String passwordWithSalt = registerDto.getPassword() + passwordSalt;

        User newUser = new User();

        newUser.setEmail(registerDto.getEmail());
        newUser.setName(registerDto.getName());
        newUser.setPass(passwordEncoder.encode(passwordWithSalt));
        newUser.setStatus("active");
        newUser.setRole(registerDto.getRole());

        userDao.createDeveloper(newUser);
    }

    @Override
    public AccessTokenDto login(LoginDto loginDto) {

        User user = userDao.findUserByEmail(loginDto.getEmail());

        if (user == null) {
            throw new BadCredentialsException();
        }

        String loginDtoPasswordWithSalt = loginDto.getPassword() + passwordSalt;

        if (!passwordEncoder.matches(loginDtoPasswordWithSalt, user.getPass()) || !user.getStatus().equals("active")) {
            throw new BadCredentialsException();
        }
        
        String token = jwtTokenProvider.createToken(user);
        return new AccessTokenDto(token);
    }


    @Override
    @Transactional
    public void changeUserPassword(ResetToken resetToken, String password) {

        String encodedPasswordWithSalt = passwordEncoder.encode(password + passwordSalt);
        userDao.changeUserPassword(resetToken, encodedPasswordWithSalt);
    }

    @Override
    @Transactional
    public boolean validatePassword(PasswordDTO passwordDTO) {
        User theUser = userDao.findUserByToken(passwordDTO.getToken());
        String encodedPasswordWithSalt = passwordDTO.getPassword() + passwordSalt;

        return passwordEncoder.matches(encodedPasswordWithSalt, theUser.getPass());
    }
}
