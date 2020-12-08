package com.ncedu.cheetahtest.entity.actscenario;

import lombok.Data;

@Data
public class ActScenario {
    private int id;
    private int actionId;
    private int testScenarioId;
    private ActStatus actStatus;
    private int parameterId;
    private String actionTitle;
    private String actionType;
    private String parameterType;
    private String parameterValue;
    private int priority;

    public ActScenario() {
        actStatus = ActStatus.INACTIVE;
    }

}
