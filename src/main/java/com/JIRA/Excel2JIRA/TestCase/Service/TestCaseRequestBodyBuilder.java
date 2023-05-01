package com.JIRA.Excel2JIRA.TestCase.Service;

import com.JIRA.Excel2JIRA.Constants.JIRARequestBodyKeys;
import com.JIRA.Excel2JIRA.CoreModel.RequestModel;
import com.JIRA.Excel2JIRA.TestCase.Model.TestCaseCoreModel;
import com.JIRA.Excel2JIRA.TestCase.Model.TestCaseStepsModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.util.*;

public class TestCaseRequestBodyBuilder {


    public void postTestCaseToJIRA(RequestModel requestModel, TestCaseCoreModel testCase){

        Map<String , Object> requestBody = new HashMap<>();

        Map<String , Object> requestBodyContent = new HashMap<>();
        List<Map<String , Object>> steps = new ArrayList<>();

        requestBodyContent.put(JIRARequestBodyKeys.PROJECT , getProjectKeyMap());
        requestBodyContent.put(JIRARequestBodyKeys.ISSUE_TYPE, TestCaseCoreModel.ISSUE_TYPE);
        requestBodyContent.put(JIRARequestBodyKeys.SUMMARY, testCase.getSummary());
        requestBodyContent.put(JIRARequestBodyKeys.DESCRIPTION, testCase.getDescription());
        requestBodyContent.put(JIRARequestBodyKeys.TEST_SET_CUSTOM_KEY , testCase.getTestSetKey());

        List<TestCaseStepsModel> fields = testCase.getTestCaseStepsModelList();

        for(TestCaseStepsModel step : fields){
            Map<String , Object > stepMap = new HashMap<>();
            Map<String , Object> fieldData = new HashMap<>();
            stepMap.put(JIRARequestBodyKeys.INDEX , step.getIndex());

            fieldData.put(JIRARequestBodyKeys.ACTION, step.getTestCaseFieldModel().getAction());
            fieldData.put(JIRARequestBodyKeys.EXPECTED_RESULT, step.getTestCaseFieldModel().getExpectedResult());

            stepMap.put(JIRARequestBodyKeys.FIELDS, fieldData);

            steps.add(stepMap);
        }

        Map<String, Object> stepToCustomField = new HashMap<>();
        stepToCustomField.put(JIRARequestBodyKeys.STEPS, steps);
        requestBodyContent.put(JIRARequestBodyKeys.TEST_CASE_STEPS_CUSTOM_KEY, stepToCustomField);

        requestBody.put(JIRARequestBodyKeys.FIELDS, requestBodyContent);

        System.out.println(requestBody);
    }

    private Map<String, String> getProjectKeyMap(){
        Map<String, String> keyMap = new HashMap<>();
        keyMap.put("key", JIRARequestBodyKeys.PROJECT_KEY);
        return keyMap;
    }
}
