package com.ncedu.cheetahtest.controller.security;

import com.ncedu.cheetahtest.entity.security.AccessTokenDto;
import com.ncedu.cheetahtest.entity.security.LoginDto;
import com.ncedu.cheetahtest.entity.security.RegisterDto;
import com.ncedu.cheetahtest.entity.security.RegisterResponse;
import com.ncedu.cheetahtest.service.security.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private AuthService authService;

    @Autowired
    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(@RequestBody RegisterDto registerDto) {
        authService.register(registerDto);

        return ResponseEntity.ok(new RegisterResponse("success"));
    }

    @PostMapping("/login")
    public ResponseEntity<AccessTokenDto> login(@RequestBody LoginDto loginDto) {
        AccessTokenDto accessTokenDto = authService.login(loginDto);

        return  ResponseEntity.ok(accessTokenDto);
    }
    
}
