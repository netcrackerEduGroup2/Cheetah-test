package com.ncedu.cheetahtest.entity.library;

import lombok.Data;

import java.util.Date;

@Data
public class Library {
    private int id;
    private String description;
     private String name;
     private Date createDate;
     public Library(){
         this.createDate = new Date();
     }
}
