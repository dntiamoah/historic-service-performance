package com.nafifsource.hsp.service_performance.batch;

import com.nafifsource.hsp.service_performance.domain.DailyPerformanceServiceRID;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricServicePerformanceMapper implements RowMapper<DailyPerformanceServiceRID> {

    public static final String ID_COLUMN = "trainuid";
    public static final String RID_COLUMN = "rid";

    @Override
    public DailyPerformanceServiceRID mapRow(ResultSet rs, int rowNum) throws SQLException {

        return DailyPerformanceServiceRID.builder()
                .trainUid(rs.getString(ID_COLUMN))
                .rid(rs.getString(RID_COLUMN))
                .build();
    }
}
