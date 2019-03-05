package com.example.demo;

import com.google.common.collect.Maps;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Map;
import java.util.TimeZone;

@Service
public class TestReportService {

    @Autowired
    private TestReportRepository testReportRepository;

    ArrayList<TestReportViewModel> testReportView;

    public ArrayList<TestReportViewModel> compareConnectorQAFile(TestCaseDetails testCaseDetails)
    {
        String connectorOutputDecoded = new String(Base64.decodeBase64(testCaseDetails.getConnectorOutputBase64()));

        String pregeneratedOutputDecoded = new String(Base64.decodeBase64(testCaseDetails.getPregeneratedOutputBase64()));

        Gson g = new Gson();
        Type mapType = new TypeToken<Map<String, Object>>() {
        }.getType();
        Map<String, Object> firstMap = g.fromJson(connectorOutputDecoded, mapType);
        Map<String, Object> secondMap = g.fromJson(pregeneratedOutputDecoded,mapType);

        testReportView=new ArrayList<>();

        if(Maps.difference(firstMap, secondMap).areEqual())
        {
            System.out.println("Both files match fully");

            populateStatModel(testCaseDetails.getTestCaseId(),"Matching Files","","",
                    "","PASS");

        }
        else {
            System.out.println("Entries with different value are as follows:-");
            Maps.difference(firstMap, secondMap).entriesDiffering().forEach((k, v) -> {
                System.out.println("Connector Value :-"+v.leftValue().toString());
                System.out.println("QA Value:-"+v.rightValue().toString());
                System.out.println("Field name:-" + k + " Value is:-" + v);

                populateViewModel("Different Value",k,v.leftValue().toString(),v.rightValue().toString());
                System.out.println(v.toString());
                populateStatModel(testCaseDetails.getTestCaseId(),"Different Value",k,
                        v.leftValue().toString(),v.rightValue().toString(),"FAIL");

            });

            System.out.println("Entries only on left side are as follows are as follows:-");
            Maps.difference(firstMap, secondMap).entriesOnlyOnLeft().forEach((k, v) -> {

                System.out.println("Connector Value :-"+v.toString());

                populateViewModel("Only in Connector",k,v.toString(),"");

                populateStatModel(testCaseDetails.getTestCaseId(),"Only in Connector",k,v.toString(),
                        "","FAIL");
            });

            System.out.println("Entries only on right side are as follows are as follows:-");
            Maps.difference(firstMap, secondMap).entriesOnlyOnRight().forEach((k, v) -> {

                System.out.println("QA Value :-"+v.toString());
                populateViewModel("Only in QA",k,"",v.toString());

                populateStatModel(testCaseDetails.getTestCaseId(),"Only in QA",k,
                        "",v.toString(),"FAIL");
            });
        }
        return testReportView;
    }

    private String getCurrentTime(){
        Calendar aGMTCalendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        return aGMTCalendar.getTime().toString();
    }

    private void populateViewModel(String category, String fieldName, String leftValue, String rightValue) {
        TestReportViewModel testReportViewModel = new TestReportViewModel();
        testReportViewModel.setCategory(category);
        testReportViewModel.setFieldName(fieldName);
        testReportViewModel.setLeftValue(leftValue);
        testReportViewModel.setRightValue(rightValue);
        testReportView.add(testReportViewModel);
    }

    private void populateStatModel(String testCaseId, String category, String fieldName, String leftValue,
                                   String rightValue, String status) {

        TestReportStatModel testReportStatModel=new TestReportStatModel();
        testReportStatModel.setCategory(category);
        testReportStatModel.setTestCaseId(testCaseId);
        testReportStatModel.setTestStatus(status);
        testReportStatModel.setTimestamp(getCurrentTime());
        testReportStatModel.setFieldName(fieldName);
        testReportStatModel.setLeftValue(leftValue);
        testReportStatModel.setRightValue(rightValue);
        testReportRepository.save(testReportStatModel);
    }
}
