package com.JIRA.Excel2JIRA.TestCase.Service;

import com.JIRA.Excel2JIRA.Constants.ExcelColumnConstants;
import com.JIRA.Excel2JIRA.CoreModel.RequestModel;
import com.JIRA.Excel2JIRA.CoreUtilities.ExcelParser;
import com.JIRA.Excel2JIRA.TestCase.Model.TestCaseCoreModel;
import com.JIRA.Excel2JIRA.TestCase.Model.TestCaseFieldModel;
import com.JIRA.Excel2JIRA.TestCase.Model.TestCaseStepsModel;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.logging.Logger;

@Service
public class TestCaseProcessor {

    @Autowired
    private TestCaseRequestBodyBuilder testCaseRequestBodyBuilder;

    private static final Logger LOGGER = Logger.getLogger("TestCaseProcessor");
    public void startProcess(RequestModel requestModel){

        try{
            ExcelParser excelParser = new ExcelParser();
            excelParser.loadWorkBook(requestModel.getAbsolutePath());

            int totalSheets = excelParser.getTotalNumberOfSheets();

            //parsing sheet by sheet
            for(int sheetIndex = 0; sheetIndex < totalSheets; sheetIndex++){
                List<Integer> startPoints = new ArrayList<>();
                excelParser.changeActiveSheet(sheetIndex);
                XSSFSheet currentSheet = excelParser.getActiveSheet();

                // Marking Points for data
                for(int rowIndex = 1; rowIndex < currentSheet.getLastRowNum(); rowIndex++){

                    String testCaseId = excelParser.getCellValueInString(ExcelColumnConstants.TEST_CASE_ID_COLUMN , rowIndex);

                    if(!testCaseId.isEmpty() && testCaseId.contains("TC")){
                        LOGGER.info(currentSheet.getSheetName() + " : " + String.valueOf(rowIndex));
                        startPoints.add(rowIndex);
                    }

                }

                startPoints.add(currentSheet.getLastRowNum()+1);

                //parsing test cases in a sheet
                for(int index = 0 ; index < startPoints.size()-1 ; index++){

                    TestCaseCoreModel testCase = new TestCaseCoreModel();

                    int startPoint = startPoints.get(index);
                    int endPoint = startPoints.get(index + 1);

                    //setting testcase headings, description, test steps
                    testCase.setSummary(getSummary(excelParser , startPoint));
                    testCase.setDescription(excelParser.getCellValueInString(ExcelColumnConstants.TEST_CASE_DESCRIPTION_COLUMN, startPoint));
                    testCase.setTestSetKey(Collections.singletonList(requestModel.getTestSetKey()));
                    testCase.setTestCaseStepsModelList(getTestSteps(excelParser, startPoint, endPoint));

                    testCaseRequestBodyBuilder.postTestCaseToJIRA(requestModel, testCase);

                }

            }

            //excelParser.closeExcelFile();

        }catch (IOException ioe){

            LOGGER.info("Unable to Parse the file");

        }


    }


    private String getSummary(ExcelParser excelParser , int rowId){

        String testCaseSummary = excelParser.getCellValueInString(ExcelColumnConstants.HEADING_DESCRIPTION , rowId);

        if(testCaseSummary.contains("TC")){
            return testCaseSummary;
        }

        StringBuilder builder = new StringBuilder();

        builder.append(excelParser.getCellValueInString(ExcelColumnConstants.EPIC_NO, rowId)).append("_");
        builder.append(excelParser.getCellValueInString(ExcelColumnConstants.STORY_NUMBER, rowId)).append("_");
        builder.append(excelParser.getCellValueInString(ExcelColumnConstants.TEST_CASE_ID_COLUMN, rowId)).append("_");
        builder.append(excelParser.getCellValueInString(ExcelColumnConstants.HEADING_DESCRIPTION, rowId));


        return builder.toString();

    }

    // Get test steps and assign to objects
    private List<TestCaseStepsModel> getTestSteps(ExcelParser excelParser, int startPoint, int endPoint){

        List<TestCaseStepsModel> steps = new ArrayList<>();

        for(int entryIndex = startPoint ; entryIndex < endPoint ; entryIndex++){

            TestCaseFieldModel fields = new TestCaseFieldModel();

            String action = excelParser.getCellValueInString(ExcelColumnConstants.TEST_STEP_ACTION, entryIndex);
            String result = excelParser.getCellValueInString(ExcelColumnConstants.TEST_STEP_EXPECTED_RESULT, entryIndex);

            if(!action.isEmpty()){
                LOGGER.info("current test case index : " + String.valueOf(startPoint)
                        + " current step : " + String.valueOf(entryIndex));
                fields.setAction(action);
            }

            if(!result.isEmpty()){
                fields.setExpectedResult(result);
            }

            if(!action.isEmpty() && !result.isEmpty()){
                TestCaseStepsModel step = new TestCaseStepsModel();

                step.setIndex(entryIndex - startPoint + 1);
                step.setTestCaseFieldModel(fields);
                steps.add(step);

            }

        }

        return steps;
    }
}
