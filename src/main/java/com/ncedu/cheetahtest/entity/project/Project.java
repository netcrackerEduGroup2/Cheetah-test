package com.ncedu.cheetahtest.entity.project;

import lombok.Data;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
public class Project {
    private int id;
    private String name;
    private String link;
    private String status;
    private Timestamp createDate;

    public Project(int id, String name, String link, String status, Timestamp createDate) {
        this.id = id;
        this.name = name;
        this.link = link;
        this.status = status;
        this.createDate = createDate;
    }
}
