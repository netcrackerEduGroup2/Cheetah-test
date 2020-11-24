package com.ncedu.cheetahtest.entity.compound;

import lombok.Data;

@Data
public class Compound {
    private int id;
    private String title;
    private String description;
    private int idTestScenario;
    private String status;
}
