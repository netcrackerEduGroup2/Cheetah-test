package com.ncedu.cheetahtest.entity.compound;

import lombok.Data;

@Data
public class CreateCompoundResponse {
    private String status;

    public CreateCompoundResponse(String status) {
        this.status = status;
    }
}
