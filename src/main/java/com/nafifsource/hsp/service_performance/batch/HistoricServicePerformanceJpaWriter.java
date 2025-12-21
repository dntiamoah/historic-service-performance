package com.nafifsource.hsp.service_performance.batch;

import com.nafifsource.hsp.service_performance.domain.BasicScheduleDailyPerformance;
import com.nafifsource.hsp.service_performance.domain.BasicScheduleDailyPerformanceHeader;
import com.nafifsource.hsp.service_performance.domain.BasicScheduleLocationDailyPerformance;
import com.nafifsource.hsp.service_performance.domain.BasicScheduleLocationDailyPerformanceId;
import com.nafifsource.hsp.service_performance.domain.dao.BasicScheduleDailyPerformanceHeaderDAO;
import com.nafifsource.hsp.service_performance.domain.dao.BasicScheduleLocationDailyPerformanceDAO;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

@Service
public class HistoricServicePerformanceJpaWriter implements ItemWriter<BasicScheduleDailyPerformance> {

    private final BasicScheduleDailyPerformanceHeaderDAO basicDailyScheduleDAO;
    private final BasicScheduleLocationDailyPerformanceDAO locationDailyPerformanceDAO;

    public HistoricServicePerformanceJpaWriter(BasicScheduleDailyPerformanceHeaderDAO basicDailyScheduleDAO, BasicScheduleLocationDailyPerformanceDAO locationDailyPerformanceDAO) {
        this.basicDailyScheduleDAO = basicDailyScheduleDAO;
        this.locationDailyPerformanceDAO = locationDailyPerformanceDAO;
    }

    @Override
    public void write(@NonNull Chunk<? extends BasicScheduleDailyPerformance> chunk) throws Exception {

        for (BasicScheduleDailyPerformance item : chunk) {
            // order locations by published time of arrival ascending to get order correct
            basicDailyScheduleDAO.save(
                    BasicScheduleDailyPerformanceHeader.builder()
                            .dateOfService(item.getDateOfService())
                            .rid(item.getRid())
                            .tocCode(item.getTocCode())
                            .originCrs(item.getLocations().stream()
                                    .findFirst()
                                    .map(BasicScheduleLocationDailyPerformance::getId)
                                    .map(BasicScheduleLocationDailyPerformanceId::getLocation)
                                    .orElse(null))
                            .terminateCrs(item.getLocations().stream()
                                    .reduce((first, second) -> second)
                                    .map(BasicScheduleLocationDailyPerformance::getId)
                                    .map(BasicScheduleLocationDailyPerformanceId::getLocation)
                                    .orElse(null))
                            .build());
            locationDailyPerformanceDAO.saveAll(item.getLocations());
        }


    }
}
