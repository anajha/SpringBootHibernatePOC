package com.example.demo;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity // This tells Hibernate to make a table out of this class
@Table(name="JOB")
public class JobDetails {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)

    public Long id;

    @Column(name="JOBID")
    public String jobId;

    @Column(name="JOBNAME")
    public String jobName;

    @Column(name="JOBSTATUS")
    public String jobStatus;

    @Column(name="JOBSTATENO")
    public String jobStateNo;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getJobId() {
        return jobId;
    }

    public void setJobId(String jobId) {
        this.jobId = jobId;
    }

    public String getJobName() {
        return jobName;
    }

    public void setJobName(String jobName) {
        this.jobName = jobName;
    }

    public String getJobStatus() {
        return jobStatus;
    }

    public void setJobStatus(String jobStatus) {
        this.jobStatus = jobStatus;
    }

    public String getJobStateNo() {
        return jobStateNo;
    }

    public void setJobStateNo(String jobStateNo) {
        this.jobStateNo = jobStateNo;
    }
}
