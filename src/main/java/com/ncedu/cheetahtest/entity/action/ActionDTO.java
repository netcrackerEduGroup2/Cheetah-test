package com.ncedu.cheetahtest.entity.action;

import lombok.Data;

@Data
public class ActionDTO {
    private String title;
    private String description;
    private int idCompound;
    private int idTestScenario;
    private String status;
}
