package com.ncedu.cheetahtest.entity.compscenario;

import com.ncedu.cheetahtest.entity.actscenario.ActStatus;
import com.ncedu.cheetahtest.entity.scenario.Scenario;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class CompScenario extends Scenario {
    private int id;
    private int idCompound;
    private int idTestScenario;
    private ActStatus status;
    private String title;

    public CompScenario() {
        status = ActStatus.INACTIVE;
    }

    @Override
    public String toString() {
        return "CompScenario{" +
                "id=" + id +
                ", idCompound=" + idCompound +
                ", idTestScenario=" + idTestScenario +
                ", status=" + status +
                ", title='" + title + '\'' +
                ", prior='" + getPriority() + '\'' +
                '}';
    }
}
