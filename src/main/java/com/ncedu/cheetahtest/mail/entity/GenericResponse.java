package com.ncedu.cheetahtest.mail.entity;

import lombok.Data;

@Data
public class GenericResponse {
    private String message;

    public GenericResponse(String message) {
        this.message = message;
    }
}