package com.ncedu.cheetahtest.mail.model;

import java.util.Date;

public class PasswordResetToken {

    private static final int EXPIRATION = 60 * 24;

    private Long id;

    private String token;

    //    private User user;

    private Date expiryDate;
}
