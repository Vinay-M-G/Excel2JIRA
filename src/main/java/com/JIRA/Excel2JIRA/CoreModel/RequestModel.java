package com.JIRA.Excel2JIRA.CoreModel;

public class RequestModel {

    private String accessToken;
    private String absolutePath;
    private String testSetKey;

    public String getAccessToken()
    {
        return accessToken;
    }

    public void setAccessToken(String accessToken)
    {
        this.accessToken = accessToken;
    }

    public String getAbsolutePath()
    {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath)
    {
        this.absolutePath = absolutePath;
    }

    public String getTestSetKey()
    {
        return testSetKey;
    }

    public void setTestSetKey(String testSetKey)
    {
        this.testSetKey = testSetKey;
    }

}
