package org.example.dto;

public class ErrorMessage {
    private String errorContent;
    private int errorCode;
    private String documentationUrl;


    public String getErrorContent() {
        return errorContent;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public void setErrorContent(String errorContent) {
        this.errorContent = errorContent;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public void setDocumentationUrl(String documentationUrl) {
        this.documentationUrl = documentationUrl;
    }
}
