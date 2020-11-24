package com.ncedu.cheetahtest.entity.action;

import lombok.Data;

@Data
public class Action {
    private int id;
    private String title;
    private String description;
    private int idCompound;
    private int idTestScenario;
    private String status;
}
