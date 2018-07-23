package com.stu.dmt.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stu.dmt.model.Monitoring;
import com.stu.dmt.repository.MonitoringJdbcRepository;

@Service
public class MonitorJdbcService {
	
	@Autowired
	private MonitoringJdbcRepository dao;
	
	@Transactional(readOnly = false)
	public void update(List<Monitoring> params) {
		dao.update(params);
	}
}
