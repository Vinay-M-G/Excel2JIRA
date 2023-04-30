package com.JIRA.Excel2JIRA.Constants;

import org.springframework.beans.factory.annotation.Value;

public class JIRARequestBodyKeys {

    @Value("${JIRA.CustomFields.TestCaseSteps}")
    private static String TEST_CASE_STEPS_CUSTOM_KEY;

    @Value("${JIRA.CustomFields.TestSet}")
    private static String TEST_SET_CUSTOM_KEY;

    private static final String PROJECT = "project";
    private static final String SUMMARY = "summary";
    private static final String DESCRIPTION = "description";
    private static final String ISSUE_TYPE = "issueType";
    private static final String STEPS = "steps";
    private static final String FIELDS = "fields";
    private static final String INDEX = "index";

    private JIRARequestBodyKeys(){

    }
}
