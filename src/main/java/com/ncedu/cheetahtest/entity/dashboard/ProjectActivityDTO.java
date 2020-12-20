package com.ncedu.cheetahtest.entity.dashboard;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ProjectActivityDTO {
    private String date;
    private int count;
}
