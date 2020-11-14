package com.ncedu.cheetahtest.mail.entity;

import lombok.Data;

@Data
public class GenericResponse {
    private String status;

    public GenericResponse(String status) {
        this.status = status;
    }
}