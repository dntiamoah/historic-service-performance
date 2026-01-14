package com.nafifsource.hsp.service_performance.batch.supplier;

import com.nafifsource.hsp.service_performance.domain.HistoricServicePerformanceRetry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricServicePerformanceRetryMapper implements RowMapper<HistoricServicePerformanceRetry> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceRetryMapper.class);
    public static final String ID_COLUMN = "date_of_service";
    public static final String RID_COLUMN = "retry";

    @Override
    public HistoricServicePerformanceRetry mapRow(ResultSet rs, int rowNum) throws SQLException {

        LOGGER.info("HistoricServicePerformanceRetry trainUId: {}, rid: {}",
                rs.getString(ID_COLUMN),
                rs.getString(RID_COLUMN));
        return HistoricServicePerformanceRetry.builder()
                .dateOfService(rs.getDate(ID_COLUMN).toLocalDate())
                .retry(rs.getBoolean(RID_COLUMN))
                .build();
    }
}
