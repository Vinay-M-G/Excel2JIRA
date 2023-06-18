package com.JIRA.Excel2JIRA.CoreUtilities;

import com.JIRA.Excel2JIRA.CoreModel.RequestModel;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;
import java.util.logging.Logger;

public class JIRAClient {

    private static final Logger LOGGER = Logger.getLogger("JIRAClient");

    public Boolean postTestCaseData(RequestModel requestModel, Map<String, Object> requestBody){

        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();

        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        headers.setBearerAuth(requestModel.getAccessToken());

        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);
        ResponseEntity<String> response = restTemplate.postForEntity(requestModel.getJIRAUrl(), entity, String.class);

        if(response.getStatusCode().is2xxSuccessful()){
            LOGGER.info(response.getBody());
            return true;
        }

        LOGGER.info(response.getBody());
        return false;
    }
}
