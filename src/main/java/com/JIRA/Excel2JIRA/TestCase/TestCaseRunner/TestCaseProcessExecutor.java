package com.JIRA.Excel2JIRA.TestCase.TestCaseRunner;

import com.JIRA.Excel2JIRA.CoreModel.RequestModel;
import com.JIRA.Excel2JIRA.TestCase.Service.TestCaseProcessor;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.logging.Logger;

public class TestCaseProcessExecutor implements Runnable{

    private static final Logger LOGGER = Logger.getLogger("TestCaseProcessExecutor");
    private RequestModel requestModel;

    public TestCaseProcessExecutor(RequestModel requestModel){
        this.requestModel = requestModel;
    }

    @Override
    public void run(){
        LOGGER.info("Started Process to Upload Test Cases");
        TestCaseProcessor testCaseProcessor = new TestCaseProcessor();
        testCaseProcessor.startProcess(requestModel);
    }

}
