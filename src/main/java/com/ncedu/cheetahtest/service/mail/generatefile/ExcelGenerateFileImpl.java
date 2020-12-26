package com.ncedu.cheetahtest.service.mail.generatefile;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import static com.ncedu.cheetahtest.service.mail.MailConsts.BR;

@Slf4j
@Component
public class ExcelGenerateFileImpl implements GenerateFile, GenerateExcel {

    private Workbook workbook;
    private Sheet sheet;
    private Row row;
    private XSSFFont font;
    private CellStyle headerStyle;

    @Autowired
    public ExcelGenerateFileImpl() {
        this.workbook = new XSSFWorkbook();
        this.headerStyle = this.workbook.createCellStyle();
    }

    @Override
    public void createFile(String filePath) throws FileNotFoundException, IOException {
        FileOutputStream outputStream = new FileOutputStream(filePath);
        workbook.write(outputStream);
    }

    @Override
    public void closeFile() { }

    @Override
    public void createSheet(String name) {
        this.sheet = this.workbook.createSheet(name);
    }

    @Override
    public void createRow(int num) {
        this.row = this.sheet.createRow(num);
    }

    @Override
    public void createCellStyle() {
        this.headerStyle = workbook.createCellStyle();
    }


    @Override
    public void createFont(String name, int height, boolean isBold) {
        XSSFFont font = ((XSSFWorkbook) workbook).createFont();
        font.setFontName(name);
        font.setFontHeightInPoints((short) height);
        font.setBold(true);
        headerStyle.setFont(font);
    }

    @Override
    public void createCell(int num, String value) {
        Cell headerCell = row.createCell(num);
        headerCell.setCellValue(value);
        headerCell.setCellStyle(this.headerStyle);
    }

    @Override
    public void setFillCellColor(short index) {
        this.createCellStyle();
        this.headerStyle.setFillForegroundColor(index);
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
    }

    @Override
    public void createSheetAndSetRowName(String sheetName) {
        this.createSheet(sheetName);
        this.createRow(0);
        this.setFillCellColor(IndexedColors.TAN.getIndex());
        this.createFont("Arial", 16, true);
        this.createCell(0, "Title");
        this.createCell(1, "Type");
        this.createCell(2, "Description");
        this.createCell(3, "Result");
        this.createFont("Arial", 16, false);
    }

    @Override
    public String createRepeatable(int numRow, String executionDate) {
        this.createRow(numRow);
        this.createCell(0, executionDate);
        return "<p>Execution date:</p>> " + executionDate + BR;
    }
}
