package com.nafifsource.hsp.service_performance.batch.supplier;

import com.nafifsource.hsp.service_performance.domain.HistoricServicePerformanceErrorResponse;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricServicePerformanceErrorResponseMapper implements RowMapper<HistoricServicePerformanceErrorResponse> {

    private static final String RID = "rid";
    private static final String TRAIN_UID = "trainuid";
    private static final String DATE_OF_SERVICE = "dateOfService";
    private static final String ERROR_MESSAGE = "errorMessage";
    private static final String HTTP_STATUS = "httpStatus";
    private static final String RETRIES = "retries";
    private static final String ERROR_TIMESTAMP = "errorTimeStamp";
    private static final String LAST_RETRY = "lastRetry";
    private static final String IS_SUCCESSFUL = "isSuccessful";

    @Override
    public HistoricServicePerformanceErrorResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
        return HistoricServicePerformanceErrorResponse.builder()
                .rid(rs.getString(RID))
                .trainuid(rs.getString(TRAIN_UID))
                .dateOfService(rs.getString(DATE_OF_SERVICE))
                .errorMessage(rs.getString(ERROR_MESSAGE))
                .httpStatus(rs.getString(HTTP_STATUS))
                .retries(rs.getInt(RETRIES))
                .errorTimeStamp(rs.getTimestamp(ERROR_TIMESTAMP).toLocalDateTime())
                .lastRetry(rs.getTimestamp(LAST_RETRY).toLocalDateTime())
                .isSuccessful(rs.getBoolean(IS_SUCCESSFUL))
                .build();
    }
}
