package com.nafifsource.hsp.service_performance.domain.dao;

import com.nafifsource.hsp.service_performance.domain.HistoricServicePerformanceErrorResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface HistoricServicePerformanceErrorResponseDAO extends JpaRepository<HistoricServicePerformanceErrorResponse, String> {
}
