package com.ncedu.cheetahtest.entity.actscenario;

import com.ncedu.cheetahtest.entity.scenario.Scenario;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ActScenario extends Scenario {
    private int id;
    private int actionId;
    private int testScenarioId;
    private ActStatus actStatus;
    private int parameterId;
    private String actionTitle;
    private String actionType;
    private String parameterType;
    private String parameterValue;

    public ActScenario() {
        actStatus = ActStatus.INACTIVE;
    }

    @Override
    public String toString() {
        return "ActScenario{" +
                "id=" + id +
                ", actionId=" + actionId +
                ", testScenarioId=" + testScenarioId +
                ", actStatus=" + actStatus +
                ", parameterId=" + parameterId +
                ", actionTitle='" + actionTitle + '\'' +
                ", actionType='" + actionType + '\'' +
                ", parameterType='" + parameterType + '\'' +
                ", parameterValue='" + parameterValue + '\'' +
                ", prior='" + getPriority() + '\'' +
                '}';
    }
}
