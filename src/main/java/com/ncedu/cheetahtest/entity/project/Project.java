package com.ncedu.cheetahtest.entity.project;

import lombok.Data;

import java.util.Date;

@Data
public class Project {
    private int id;
    private String name;
    private String link;
    // TODO: 21.11.2020 Change 'status' field to ENUM type
    private String status;
    private Date createDate;
    private int idOwner;

    public Project(int id, String name, String link, String status, Date createDate, int idOwner) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.status = status;
        this.createDate = createDate;
        this.idOwner = idOwner;
    }
}
