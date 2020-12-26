package com.ncedu.cheetahtest.service.mail.generatefile;

public interface GenerateExcel {

    void createSheet(String name);

    void createRow(int num);

    void createFont(String name, int height, boolean isBold);

    void createCell(int num, String value);

    void setFillCellColor(short index);

    void createCellStyle();

    void createSheetAndSetRowName(String sheetName);

    String createRepeatable(int numRow, String executionDate);
}
