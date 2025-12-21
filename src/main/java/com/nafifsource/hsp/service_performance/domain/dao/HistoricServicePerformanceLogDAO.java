package com.nafifsource.hsp.service_performance.domain.dao;

import com.nafifsource.hsp.service_performance.domain.HistoricServicePerformanceLog;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;

public interface HistoricServicePerformanceLogDAO extends JpaRepository<HistoricServicePerformanceLog, LocalDate> {

}
