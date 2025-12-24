package com.nafifsource.hsp.service_performance.batch;

import com.nafifsource.hsp.service_performance.domain.HistoricServicePerformanceLog;
import com.nafifsource.hsp.service_performance.domain.dao.HistoricServicePerformanceLogDAO;
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
import java.time.LocalDateTime;

import static java.util.UUID.randomUUID;

@Service
public class HistoricServicePerformanceLauncher {

    private final Job collectAllHistoricServicePerformance;
    private final JobOperator jobOperator;
    private final JdbcTemplate jdbcTemplate;
    private final HistoricServicePerformanceLogDAO hspLogDAO;
    private final HistoricServicePerformanceConfig config;

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceLauncher.class);


    public HistoricServicePerformanceLauncher(Job collectAllHistoricServicePerformance,
                                              JobOperator jobOperator,
                                              JdbcTemplate jdbcTemplate,
                                              HistoricServicePerformanceLogDAO hspLogDAO, HistoricServicePerformanceConfig config) {
        this.collectAllHistoricServicePerformance = collectAllHistoricServicePerformance;
        this.jobOperator = jobOperator;
        this.jdbcTemplate = jdbcTemplate;
        this.hspLogDAO = hspLogDAO;
        this.config = config;
    }

//    * "0 0 * * * *" = the top of every hour of every day.
//    * "*/10 * * * * *" = every ten seconds.
//    * "0 0 8-10 * * *" = 8, 9 and 10 o'clock of every day.
//    * "0 0 8,10 * * *" = 8 and 10 o'clock of every day.
//    * "0 0/30 8-10 * * *" = 8:00, 8:30, 9:00, 9:30 and 10 o'clock every day.
//    * "0 0 9-17 * * MON-FRI" = on the hour nine-to-five weekdays
//    * "0 0 0 25 12 ?" = every Christmas Day at midnight

    @Scheduled(cron = "${hsp.schedule.minute}")
    void loadAllAvailableHistoricServicePerformanceData() throws JobInstanceAlreadyCompleteException,
            JobExecutionAlreadyRunningException,
            JobRestartException,
            InvalidJobParametersException {


        LocalDate yesterday = LocalDate.now().minusDays(1);
        LocalDate minDate = jdbcTemplate.queryForObject(
                "SELECT MIN(date_runs_from) FROM mca_basic_schedule", LocalDate.class);

        LOGGER.info("HSP Daily Basic Schedule - Start from: {} to {}", yesterday, minDate);

        for (LocalDate date = yesterday; !date.isBefore(minDate); date = date.minusDays(1)) {
            HistoricServicePerformanceLog.HistoricServicePerformanceLogBuilder hspLog = HistoricServicePerformanceLog.builder();
            hspLog.historicServicePerformanceDate(date)
                    .collectionStartDate(LocalDateTime.now())
                    .isComplete(false);
            config.setTheDate(date.toString());
            JobParameters jobParameters = new JobParametersBuilder()
                    .addString("collectAllHistoricServicePerformanceRunId", randomUUID().toString(), true)
                    .addString("theDate", date.toString(), true)
                    .toJobParameters();
            jobOperator.start(collectAllHistoricServicePerformance, jobParameters);

            hspLog.collectionEndDate(LocalDateTime.now())
                    .isComplete(true);
            LOGGER.info("HSP Daily Basic Schedule - Finish: {}", yesterday);
            hspLogDAO.save(hspLog.build());
        }
        LOGGER.info("HSP Daily Basic Schedule ingested: ");
    }
}