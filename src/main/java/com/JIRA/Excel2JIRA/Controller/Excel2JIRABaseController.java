package com.JIRA.Excel2JIRA.Controller;

import com.JIRA.Excel2JIRA.CoreModel.RequestModel;
import com.JIRA.Excel2JIRA.TestCase.Service.TestCaseProcessor;
import com.JIRA.Excel2JIRA.TestCase.TestCaseRunner.TestCaseProcessExecutor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api/v1")
public class Excel2JIRABaseController {

    @PostMapping(value = "/testcase")
    public ResponseEntity<?> acceptAndStartUploadProcess(
            @RequestBody final RequestModel requestModel
            ){
        Thread testCaseThread = new Thread(new TestCaseProcessExecutor(requestModel));
        System.out.println(requestModel.getJIRAUrl());
        testCaseThread.start();
        return ResponseEntity.accepted().body(requestModel);
    }

}
