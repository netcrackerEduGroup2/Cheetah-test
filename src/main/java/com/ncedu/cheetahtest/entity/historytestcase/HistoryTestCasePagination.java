package com.ncedu.cheetahtest.entity.historytestcase;

import lombok.Data;

import java.util.List;

@Data
public class HistoryTestCasePagination {
    private List<HistoryTestCaseDto> historyTestCases;
    private int totalTestCases;

    public HistoryTestCasePagination(List<HistoryTestCaseDto> historyTestCaseDtos, int totalTestCases) {
        this.historyTestCases = historyTestCaseDtos;
        this.totalTestCases = totalTestCases;
    }
}
