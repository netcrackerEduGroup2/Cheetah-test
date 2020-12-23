package com.ncedu.cheetahtest.entity.dashboard;

import lombok.Data;

@Data
public class PlannedTestCaseDTO {
    private  int idProject;
    private String titleProject;
    private  int idTestCase;
    private String titleTestCase;
    private String cronDate;
}
