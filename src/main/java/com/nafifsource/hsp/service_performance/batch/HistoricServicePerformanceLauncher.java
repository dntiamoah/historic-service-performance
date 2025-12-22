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
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

import static java.util.UUID.randomUUID;

@Service
public class HistoricServicePerformanceLauncher {

    private final Job hspBasicScheduleJob;
    private final JobOperator jobOperator;
    private final JdbcTemplate jdbcTemplate;

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceLauncher.class);


    public HistoricServicePerformanceLauncher(Job hspBasicScheduleJob, JobOperator jobOperator, JdbcTemplate jdbcTemplate) {
        this.hspBasicScheduleJob = hspBasicScheduleJob;
        this.jobOperator = jobOperator;
        this.jdbcTemplate = jdbcTemplate;
    }

//    * "0 0 * * * *" = the top of every hour of every day.
//    * "*/10 * * * * *" = every ten seconds.
//    * "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
//    * "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
//    * "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
//    * "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
//    * "0 0 0 25 12 ?" = every Christmas Day at midnight

    @Scheduled(cron = "0 0 22 * * *")
    void loadAllAvailableHistoricServicePerformanceData(LocalDate theDate) throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            InvalidJobParametersException {


        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate minDate = jdbcTemplate.queryForObject(
                "SELECT MIN(date_runs_from) FROM mca_basic_schedule", LocalDate.class);

        LOGGER.info("HSP Daily Basic Schedule - Start from: {} to {}", yesterday, minDate);

        for (LocalDate date = yesterday; !date.isBefore(minDate); date = date.minusDays(1)) {
            JobParameters hspParameters = new JobParametersBuilder()
                    .addString("hspDataLoadJob-runId", randomUUID().toString(), true)
                    .addString("theDate", theDate.toString(), true)
                    .toJobParameters();
            jobOperator.start(hspBasicScheduleJob, hspParameters);

            jdbcTemplate.update("INSERT INTO hspcollectionlog (processed_date) VALUES (?)", date);
        }
        LOGGER.info("HSP Daily Basic Schedule ingested: ");
    }
}