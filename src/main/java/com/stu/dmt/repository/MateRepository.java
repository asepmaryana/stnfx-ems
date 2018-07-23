package com.stu.dmt.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.stu.dmt.model.Mate;

public interface MateRepository extends JpaRepository<Mate, Integer> {
	
	public Mate findByEnabledIsTrue();
	
}
