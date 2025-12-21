package com.nafifsource.hsp.service_performance.domain.dao;

import com.nafifsource.hsp.service_performance.domain.BasicScheduleDailyPerformanceHeader;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BasicScheduleDailyPerformanceHeaderDAO extends JpaRepository<BasicScheduleDailyPerformanceHeader, String> {
}
