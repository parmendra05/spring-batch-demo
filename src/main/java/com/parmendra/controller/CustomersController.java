package com.parmendra.controller;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/jobs/")
public class CustomersController {

    @Autowired
    private Job job;

    @Autowired
    private JobLauncher jobLauncher;

    @GetMapping("/start")
    public String inportCsvToDBjob() throws JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
         JobParameters jobParameters = new JobParametersBuilder()
                 .addLong("started at ", System.currentTimeMillis())
                 .toJobParameters();

            jobLauncher.run(job , jobParameters);

            return "ob is triggered via API call, Please check the Customers Table inside Database";

    }
}
