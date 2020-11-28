package com.ncedu.cheetahtest.entity.actscenario;

import lombok.Data;

@Data
public class ActScenario {
    private int id;
    private int ActionId;
    private int TestScenarioId;
    private int priority;
    private ActStatus actStatus;
    private int ParameterId;
    private String actionTitle;
    private String actionType;
    private String parameterType;
    private String parameterValue;

    public ActScenario() {
        actStatus = ActStatus.INACTIVE;
    }
}
