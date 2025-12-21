package com.nafifsource.hsp.service_performance.domain.dao;

import com.nafifsource.hsp.service_performance.domain.BasicScheduleLocationDailyPerformance;
import com.nafifsource.hsp.service_performance.domain.BasicScheduleLocationDailyPerformanceId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicScheduleLocationDailyPerformanceDAO extends JpaRepository<BasicScheduleLocationDailyPerformance, BasicScheduleLocationDailyPerformanceId> {
}
