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
import org.springframework.beans.factory.annotation.Value;
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
    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceBatchConfig.class);


    public HistoricServicePerformanceBatchConfig(HistoricServicePerformanceJpaWriter hspWriter,
                                                 HistoricServicePerformanceProcessor hspProcessor) {
        this.hspWriter = hspWriter;
        this.hspProcessor = hspProcessor;
    }

    @Bean
    @StepScope
    public JdbcPagingItemReader<DailyPerformanceServiceRID> itemReader(DataSource dataSource,
                                                                       PagingQueryProvider queryProvider,
                                                                       @Value("#{jobParameters['theDate']}") String theDate) throws Exception {
        final LocalDate yesterday = LocalDate.parse(theDate);
        Map<String, Object> parameterValues = new HashMap<>();
        final String daysRun = DaysRunEnum.valueOf(yesterday.getDayOfWeek().name()).getLabel();
        parameterValues.put("daysRun", daysRun);
        parameterValues.put("yesterday", yesterday);

        return new JdbcPagingItemReaderBuilder<DailyPerformanceServiceRID>()
                .name("serviceMetric")
                .dataSource(dataSource)
                .queryProvider(queryProvider)
                .parameterValues(parameterValues)
                .rowMapper(new HistoricServicePerformanceMapper())
                .pageSize(1000)
                .build();
    }

    @Bean
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

        return provider.getObject();
    }

    @Bean
    public ItemProcessor<DailyPerformanceServiceRID, BasicScheduleDailyPerformance> hspProcessor() {
        return hspProcessor;
    }

    @Bean
    public ItemWriter<BasicScheduleDailyPerformance> hspWriter() {
        return hspWriter;
    }

    @Bean
    public Step hspBasicScheduleStep(JobRepository jobRepository,
                                     PlatformTransactionManager transactionManager,
                                     JdbcPagingItemReader<DailyPerformanceServiceRID> itemReader) {
        return new StepBuilder("hspBasicScheduleStep", jobRepository)
                .<DailyPerformanceServiceRID, BasicScheduleDailyPerformance>chunk(20)
                .transactionManager(transactionManager)
                .reader(itemReader)
                .processor(hspProcessor())
                .writer(hspWriter())
                .build();
    }

    @Bean
    public Job hspBasicScheduleJob(JobRepository jobRepository,
                                   PlatformTransactionManager transactionManager,
                                   JdbcPagingItemReader<DailyPerformanceServiceRID> itemReader) {
        return new JobBuilder("hspBasicScheduleJob", jobRepository)
                .incrementer(new RunIdIncrementer())
                .start(hspBasicScheduleStep(jobRepository, transactionManager, itemReader))
                .build();
    }
}
