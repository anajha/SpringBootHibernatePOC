package com.example.demo;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.apache.commons.io.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController()
public class JobController {

    @Autowired
    private JobDetailsRepository jobDetailsRepository;

    @GetMapping("/api/v1/source/generateFile")
    public String getFiles(@RequestParam("sourceName")String sourceName,@RequestParam("recordCount")String recordCount) throws Exception
    {
        ObjectMapper objectMapper =new ObjectMapper();
        FileResponse fileResponse=new FileResponse();

        if(sourceName.equals("Acxiom")) {
            byte[] fileContent;
            fileContent = FileUtils.readFileToByteArray(new File("Acxiom_Source_54.csv"));
            fileResponse.setFileName("Acxiom_Source_54.csv");
            String encodedString = Base64.encodeBase64String(fileContent).split("/")[1];
            fileResponse.setBase64Data(encodedString);
        }
        else if(sourceName.equals("Zipari")){
            byte[] fileContent;
            fileContent = FileUtils.readFileToByteArray(new File("Zipari_Source_3.csv"));
            fileResponse.setFileName("Zipari_Source_3.csv");
            System.out.println(Base64.encodeBase64String(fileContent));
            String encodedString = Base64.encodeBase64String(fileContent);
            fileResponse.setBase64Data(encodedString);
        }

        return objectMapper.writeValueAsString(fileResponse);
    }

    @GetMapping("/api/v1/source/getSourceNames")
    public String getSourceNames() throws Exception
    {
        ObjectMapper objectMapper =new ObjectMapper();
        List<String> listOfSources= Arrays.asList("Acxiom","Zipari","Pager","Orion");
        return objectMapper.writeValueAsString(listOfSources);
    }

    @GetMapping("/api/v1/source/getSourceDetails")
    public String getSourceDetails(@RequestParam("sourceName")String sourceName) throws Exception
    {
        System.out.println(sourceName);
        ObjectMapper objectMapper =new ObjectMapper();
        FileFormat fileFormat=new FileFormat();
        if(sourceName.equals("Acxiom"))
        {
            System.out.println("Acxiom has been selected");
            fileFormat.setFileFormat("CSV");
            fileFormat.setFileFormatDelimiter("Pipe Delimited");
        }
        else if(sourceName.equals("Zipari")){
            System.out.println("Zipari has been selected");
            fileFormat.setFileFormat("CSV");
            fileFormat.setFileFormatDelimiter("Tab Delimited");
    }
        return objectMapper.writeValueAsString(fileFormat);
    }

    @GetMapping("/api/v1/source/getFileFormats")
    public String getFileFormats() throws Exception
    {

        ObjectMapper objectMapper =new ObjectMapper();

        ArrayList fileFormats=new ArrayList<>();
        fileFormats.add("JSON");
        fileFormats.add("CSV");
        fileFormats.add("XML");

        return objectMapper.writeValueAsString(fileFormats);

    }

    @GetMapping("/api/v1/source/getFileFormatDelimiters")
    public String getFileFormatDelimiters() throws Exception
    {

        ObjectMapper objectMapper =new ObjectMapper();

        ArrayList fileFormats=new ArrayList<>();
        fileFormats.add("Comma Separated");
        fileFormats.add("Pipe Delimited");
        fileFormats.add("Tab Delimited");

        return objectMapper.writeValueAsString(fileFormats);

    }

    @PostMapping("/api/v1/source/create")
    public String getFiles(@RequestBody FileDetails fileDetails) throws Exception
    {
        String fileName=fileDetails.getFileName();
        String base64File=fileDetails.getBase64Content();

        System.out.println(base64File);

        String newRadFile = base64File.split(",")[1];
        byte[] data = Base64.decodeBase64(newRadFile);
        System.out.println("FileName:-"+fileName);
        System.out.println("File Content:-"+data);
        System.out.println("File details:-"+fileDetails.getFileFormatDelimiter());

        ObjectMapper objectMapper = new ObjectMapper();
        OutputStream os = null;
        try {
            os = new FileOutputStream(new File(fileName));
            os.write(data);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String response="Config file added successfully for "+fileDetails.getSource();
        return objectMapper.writeValueAsString(response);
    }

    @GetMapping("/getJobDetails")
    public String getJobDetails() throws Exception
    {

            ObjectMapper objectMapper =new ObjectMapper();

            List<JobDetails> jobList=jobDetailsRepository.findAll();

            return objectMapper.writeValueAsString(jobList);

    }

    @GetMapping("/getDAGDetails")
    public String getDAGtails(@RequestParam("jobID") String jobID) throws Exception
    {

        System.out.println(jobID);
        ObjectMapper objectMapper =new ObjectMapper();

        List<JobDetails> jobList=jobDetailsRepository.findByjobId(jobID);

        jobList.forEach(System.out::println);

        return objectMapper.writeValueAsString(jobList);

    }

    @PostMapping("/submitJobDetails")
    public String submitJobDetails(@RequestBody JobDetails jobDetails) throws Exception
    {

        ObjectMapper objectMapper =new ObjectMapper();

        JobDetails jobList=jobDetailsRepository.save(jobDetails);

        return objectMapper.writeValueAsString("Job Details written successfully");

    }

}
