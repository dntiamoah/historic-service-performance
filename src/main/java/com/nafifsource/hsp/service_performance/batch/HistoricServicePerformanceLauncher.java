package com.nafifsource.hsp.service_performance.batch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.InvalidJobParametersException;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.launch.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.batch.core.launch.JobRestartException;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import static java.util.UUID.randomUUID;

@Service
public class HistoricServicePerformanceLauncher {

    private final Job hspBasicScheduleJob;
    private final JobOperator jobOperator;

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceLauncher.class);


    public HistoricServicePerformanceLauncher(Job hspBasicScheduleJob, JobOperator jobOperator) {
        this.hspBasicScheduleJob = hspBasicScheduleJob;
        this.jobOperator = jobOperator;
    }

    @Scheduled
    void launchMCALoader() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            InvalidJobParametersException {


        LOGGER.info("HSP Daily Basic Schedule - Start");
        JobParameters hspParameters = new JobParametersBuilder()
                .addString("hspDataLoadJob-runId", randomUUID().toString(), true)
                .toJobParameters();
        jobOperator.start(hspBasicScheduleJob, hspParameters);
        LOGGER.info("HSP Daily Basic Schedule ingested");
    }
}