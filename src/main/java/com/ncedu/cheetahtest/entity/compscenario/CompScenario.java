package com.ncedu.cheetahtest.entity.compscenario;

import com.ncedu.cheetahtest.entity.actscenario.ActStatus;
import lombok.Data;

@Data
public class CompScenario{
    private int id;
    private int idCompound;
    private int idTestScenario;
    private ActStatus status;
    private String title;
    private int priority;

    public CompScenario() {
        status = ActStatus.INACTIVE;
    }

}
