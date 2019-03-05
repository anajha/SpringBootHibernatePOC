package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
public class TestReportController {

    @Autowired
    private TestReportService testReportService;

    @Autowired
    private TestReportRepository testReportRepository;

    @PostMapping("/api/v1/ai/testreport/create")
    public String createTestReport(@RequestBody TestCaseDetails testCaseDetails) throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        ArrayList<TestReportViewModel> testReportView=testReportService.compareConnectorQAFile(testCaseDetails);

        return objectMapper.writeValueAsString(testReportView);
    }

    @GetMapping("/api/v1/ai/testreport/view")
    public String viewTestReport() throws Exception {

        ObjectMapper objectMapper = new ObjectMapper();

        List<TestReportStatModel> testReportView=testReportRepository.findAll();

        return objectMapper.writeValueAsString(testReportView);
    }
}