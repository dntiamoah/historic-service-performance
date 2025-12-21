package com.nafifsource.hsp.service_performance.batch.supplier;

import com.nafifsource.hsp.service_performance.domain.DailyPerformanceServiceRID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HistoricServicePerformanceMapper implements RowMapper<DailyPerformanceServiceRID> {

    private static final Logger LOGGER = LoggerFactory.getLogger(HistoricServicePerformanceMapper.class);
    public static final String ID_COLUMN = "trainuid";
    public static final String RID_COLUMN = "rid";

    @Override
    public DailyPerformanceServiceRID mapRow(ResultSet rs, int rowNum) throws SQLException {

        LOGGER.info("BasicSchedule trainUId: {}, rid: {}",
                rs.getString(ID_COLUMN),
                rs.getString(RID_COLUMN));
        return DailyPerformanceServiceRID.builder()
                .trainUid(rs.getString(ID_COLUMN))
                .rid(rs.getString(RID_COLUMN))
                .build();
    }
}
