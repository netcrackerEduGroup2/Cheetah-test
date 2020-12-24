package com.ncedu.cheetahtest.entity.testcase;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class TestCasePaginated {
    private List<TestCase> testCaseList;
    private int totalElements;
}
