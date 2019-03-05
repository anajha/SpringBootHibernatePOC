package com.example.demo;

import java.util.ArrayList;

public class FileDetails {

    String fileName;
    String base64Content;
    String source;
    String fileFormat;
    String fileFormatDelimiter;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getBase64Content() {
        return base64Content;
    }

    public void setBase64Content(String base64Content) {
        this.base64Content = base64Content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getFileFormat() {
        return fileFormat;
    }

    public void setFileFormat(String fileFormat) {
        this.fileFormat = fileFormat;
    }

    public String getFileFormatDelimiter() {
        return fileFormatDelimiter;
    }

    public void setFileFormatDelimiter(String fileFormatDelimiter) {
        this.fileFormatDelimiter = fileFormatDelimiter;
    }
}
