package com.nafifsource.hsp.service_performance.batch;

import com.nafifsource.hsp.service_performance.domain.BasicScheduleDailyPerformance;
import com.nafifsource.hsp.service_performance.domain.DailyPerformanceServiceRID;
import com.nafifsource.hsp.service_performance.domain.DaysRunEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.parameters.RunIdIncrementer;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcPagingItemReader;
import org.springframework.batch.infrastructure.item.database.PagingQueryProvider;
import org.springframework.batch.infrastructure.item.database.builder.JdbcPagingItemReaderBuilder;
import org.springframework.batch.infrastructure.item.database.support.SqlPagingQueryProviderFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class HistoricServicePerformanceBatchConfig {

    private final HistoricServicePerformanceJpaWriter hspWriter;
    private final HistoricServicePerformanceProcessor hspProcessor;
    private final HistoricServicePerformanceConfig config;
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceBatchConfig.class);


    public HistoricServicePerformanceBatchConfig(HistoricServicePerformanceJpaWriter hspWriter,
                                                 HistoricServicePerformanceProcessor hspProcessor, HistoricServicePerformanceConfig config) {
        this.hspWriter = hspWriter;
        this.hspProcessor = hspProcessor;
        this.config = config;
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<DailyPerformanceServiceRID> hspItemReader(DataSource dataSource) throws Exception {
        final LocalDate yesterday = LocalDate.parse(config.getTheDate());
        Map<String, Object> parameterValues = new HashMap<>();
        final String daysRun = DaysRunEnum.valueOf(yesterday.getDayOfWeek().name()).getLabel();
        parameterValues.put("daysRun", daysRun);
        parameterValues.put("yesterday", yesterday);
        LOGGER.info("HistoricServicePerformance set itemReader {}, {}, {}", yesterday, yesterday.getDayOfWeek(), daysRun);

        return new JdbcPagingItemReaderBuilder<DailyPerformanceServiceRID>()
                .name("serviceMetric")
                .dataSource(dataSource)
                .queryProvider(queryProvider(dataSource))
                .parameterValues(parameterValues)
                .rowMapper(new HistoricServicePerformanceMapper())
                .pageSize(1000)
                .build();
    }

    @Bean
    @StepScope
    public PagingQueryProvider queryProvider(DataSource dataSource) throws Exception {
        SqlPagingQueryProviderFactoryBean provider = new SqlPagingQueryProviderFactoryBean();

        provider.setDataSource(dataSource);
        provider.setSelectClause("SELECT " +
                "DISTINCT ON (trainuid) " +
                "trainuid, " +
                "CONCAT(to_char(:yesterday,'YYYYMMDD'), ASCII(SUBSTRING(trainuid,1,2)), SUBSTRING(trainuid FROM 2 FOR LENGTH(trainuid))) AS rid ");
        provider.setFromClause("FROM public.mca_basic_schedule ");
        provider.setWhereClause("days_run LIKE :daysRun " +
                "AND date_runs_from <= :yesterday " +
                "AND date_runs_to >= :yesterday " +
                "AND train_status in ('1', 'P') ");
        provider.setSortKey("trainuid");

        LOGGER.info("HistoricServicePerformance queryProvider set");
        return provider.getObject();
    }

    @Bean
    public ItemProcessor<DailyPerformanceServiceRID, BasicScheduleDailyPerformance> hspProcessor() {
        LOGGER.info("HistoricServicePerformance PROCESSOR for: {}", config.getTheDate());
        return hspProcessor;
    }

    @Bean
    public ItemWriter<BasicScheduleDailyPerformance> hspWriter() {
        LOGGER.info("HistoricServicePerformance WRITER for: {}", config.getTheDate());
        return hspWriter;
    }

    @Bean
    public Step hspBasicScheduleStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     JdbcPagingItemReader<DailyPerformanceServiceRID> hspItemReader) {
        return new StepBuilder("hspBasicScheduleStep", jobRepository)
                .<DailyPerformanceServiceRID, BasicScheduleDailyPerformance>chunk(20)
                .transactionManager(transactionManager)
                .reader(hspItemReader)
                .processor(hspProcessor())
                .writer(hspWriter())
                .build();
    }

    @Bean
    public Job collectAllHistoricServicePerformance(JobRepository jobRepository,
                                                    PlatformTransactionManager transactionManager,
                                                    JdbcPagingItemReader<DailyPerformanceServiceRID> hspItemReader) {

        LOGGER.info("HistoricServicePerformance collector set");
        return new JobBuilder("collectAllHistoricServicePerformance", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(hspBasicScheduleStep(jobRepository, transactionManager, hspItemReader))
                .build();
    }
}
