package com.ncedu.cheetahtest.entity.library;

import lombok.Data;

@Data
public class CreateLibraryResponse {
    private String message;

    public CreateLibraryResponse(String message) {
        this.message = message;
    }
}
