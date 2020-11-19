package com.ncedu.cheetahtest.entity.security;

import lombok.Data;

@Data
public class RegisterDto {
    private String email;
    private String password;
    private String name;
    private String role;
}
