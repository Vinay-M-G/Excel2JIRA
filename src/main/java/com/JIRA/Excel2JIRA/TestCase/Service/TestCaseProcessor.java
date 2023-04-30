package com.JIRA.Excel2JIRA.TestCase.Service;

import com.JIRA.Excel2JIRA.Constants.ExcelColumnConstants;
import com.JIRA.Excel2JIRA.CoreModel.RequestModel;
import com.JIRA.Excel2JIRA.CoreUtilities.ExcelParser;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.logging.Logger;

@Service
public class TestCaseProcessor {

    private static final Logger LOGGER = Logger.getLogger("TestCaseProcessor");
    public void startProcess(RequestModel requestModel){

        try{
            ExcelParser excelParser = new ExcelParser();
            excelParser.loadWorkBook(requestModel.getAbsolutePath());

            int totalSheets = excelParser.getTotalNumberOfSheets();

            for(int sheetIndex = 0; sheetIndex < totalSheets; sheetIndex++){
                excelParser.changeActiveSheet(sheetIndex);
                XSSFSheet currentSheet = excelParser.getActiveSheet();

                for(int rowIndex = 1; rowIndex < currentSheet.getLastRowNum(); rowIndex++){

                    LOGGER.info(rowIndex + " : " + excelParser.getCellValueInString(ExcelColumnConstants.TEST_CASE_ID_COLUMN , rowIndex));
                }

            }

            excelParser.closeExcelFile();

        }catch (IOException ioe){

            LOGGER.info("Unable to Parse the file");

        }catch (Exception ex){

            LOGGER.info("Error Occured : " + ex.toString());
        }


    }
}
