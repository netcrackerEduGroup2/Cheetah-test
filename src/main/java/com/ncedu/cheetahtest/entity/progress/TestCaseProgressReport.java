package com.ncedu.cheetahtest.entity.progress;

import com.ncedu.cheetahtest.entity.selenium.ActionResult;
import lombok.Data;

import java.util.List;

@Data
public class TestCaseProgressReport {
    private int totalActionResults;
    private List<ActionResult> completed;
    private int idTestCase;
}
