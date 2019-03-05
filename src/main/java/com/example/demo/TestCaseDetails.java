package com.example.demo;

public class TestCaseDetails {

    private String testCaseId;

    private String connectorOutputBase64;

    private String pregeneratedOutputBase64;

    public String getTestCaseId() {
        return testCaseId;
    }

    public void setTestCaseId(String testCaseId) {
        this.testCaseId = testCaseId;
    }

    public String getConnectorOutputBase64() {
        return connectorOutputBase64;
    }

    public void setConnectorOutputBase64(String connectorOutputBase64) {
        this.connectorOutputBase64 = connectorOutputBase64;
    }

    public String getPregeneratedOutputBase64() {
        return pregeneratedOutputBase64;
    }

    public void setPregeneratedOutputBase64(String pregeneratedOutputBase64) {
        this.pregeneratedOutputBase64 = pregeneratedOutputBase64;
    }
}
