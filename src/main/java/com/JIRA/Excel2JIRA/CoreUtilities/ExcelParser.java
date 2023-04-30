package com.JIRA.Excel2JIRA.CoreUtilities;

import org.apache.poi.ss.formula.functions.Column;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class ExcelParser {

    private XSSFWorkbook workbook;
    private XSSFSheet activeSheet;

    public void loadWorkBook(final String path) throws IOException
    {

        try {
            FileInputStream fileInputStream = new FileInputStream(new File(path));
            this.workbook = new XSSFWorkbook(fileInputStream);

        } catch (FileNotFoundException e) {

            System.out.println("File path : " + path + " not found");

        }

    }

    public XSSFSheet getActiveSheet(){
        return activeSheet;
    }

    public int getTotalNumberOfSheets(){
        return this.workbook.getNumberOfSheets();
    }

    public void changeActiveSheet(int index){
        this.activeSheet = this.workbook.getSheetAt(index);
    }

    public String getCellValueInString(int columnId, int rowId) {
        Row row = activeSheet.getRow(rowId);
        Cell cell = row.getCell(columnId);
        String value = cell.getStringCellValue();
        return value;
    }

    public double getCellValueInDouble(int columnId, int rowId) {
        Row row = activeSheet.getRow(rowId);
        Cell cell = row.getCell(columnId);
        double value = cell.getNumericCellValue();
        return value;
    }

    public String getCellValueInDateFormat(int columnId, int rowId) {
        Row row = activeSheet.getRow(rowId);
        Cell cell = row.getCell(columnId);
        String value = cell.getDateCellValue().toString();
        return value;
    }


    public Row getEntireRow(int rowId) {
        Row row = activeSheet.getRow(rowId);
        return row;
    }

    public Column getEntireColumn(String columnId) {
        return null;
    }

    public void closeExcelFile(){
        try{
            this.workbook.close();

        }catch (IOException ioe){

        }

    }
}
