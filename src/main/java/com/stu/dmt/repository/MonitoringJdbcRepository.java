package com.stu.dmt.repository;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.stu.dmt.model.Monitoring;

@Repository
public class MonitoringJdbcRepository {
	
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public JdbcTemplate getJdbcTemplate() {
        return jdbcTemplate;
    }
    
    public void update(List<Monitoring> params) {
    	String sql = "UPDATE monitoring SET value = ?, dtime = ? WHERE id = ?";
        getJdbcTemplate().batchUpdate(sql, new BatchPreparedStatementSetter(){
        	
            @Override
            public void setValues(PreparedStatement ps, int i) throws SQLException {
            	Monitoring mon = params.get(i);
                ps.setObject(1, mon.getValue());
                ps.setTimestamp (2, new java.sql.Timestamp(mon.getDtime().getTime()));
                ps.setInt(3, mon.getId());
            }

            @Override
            public int getBatchSize() {
                return params.size();
            }
        });
    }
    
    public void update() {
    	getJdbcTemplate().update("UPDATE monitoring SET value = 0 where id > 900");
    }
}
