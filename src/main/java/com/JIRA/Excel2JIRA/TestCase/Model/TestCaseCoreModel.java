package com.JIRA.Excel2JIRA.TestCase.Model;

import java.util.Collections;
import java.util.Map;
import java.util.List;

public class TestCaseCoreModel {

    private static final Map<String , String> ISSUE_TYPE = Collections.singletonMap("name" , "Test");
    private static final Map<String, String> PROJECT_KEY = Collections.singletonMap("key" , "${JIRA.Project.Key}");

    private String summary;
    private String description;
    private List<TestCaseStepsModel> testCaseStepsModelList;
    private List<String> testSetKey;

    public String getSummary()
    {
        return summary;
    }

    public void setSummary(String summary)
    {
        this.summary = summary;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public List<TestCaseStepsModel> getTestCaseStepsModelList()
    {
        return testCaseStepsModelList;
    }

    public void setTestCaseStepsModelList(List<TestCaseStepsModel> testCaseStepsModelList)
    {
        this.testCaseStepsModelList = testCaseStepsModelList;
    }

    public List<String> getTestSetKey()
    {
        return testSetKey;
    }

    public void setTestSetKey(List<String> testSetKey)
    {
        this.testSetKey = testSetKey;
    }



}
