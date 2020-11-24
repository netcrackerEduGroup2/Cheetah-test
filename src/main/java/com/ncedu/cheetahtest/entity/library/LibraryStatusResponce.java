package com.ncedu.cheetahtest.entity.library;

import lombok.Data;

@Data
public class LibraryStatusResponce {
    private String status;

    public LibraryStatusResponce(String status) {
        this.status = status;
    }
}
