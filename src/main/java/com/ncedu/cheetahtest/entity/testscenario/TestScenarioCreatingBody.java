package com.ncedu.cheetahtest.entity.testscenario;

import lombok.Data;

import java.util.List;

@Data
public class TestScenarioCreatingBody {
    private TestScenario testScenario;
    private List<ActionsAndCompoundsID> actionsAndCompoundsDto;
}
