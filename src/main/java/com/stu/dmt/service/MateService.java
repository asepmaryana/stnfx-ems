package com.stu.dmt.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.stu.dmt.model.Mate;
import com.stu.dmt.repository.MateRepository;

@Service
@Transactional
public class MateService {
	
	@Autowired
	private MateRepository dao;
	
	public Mate getEnabled() {
		return dao.findByEnabledIsTrue();
	}
}
