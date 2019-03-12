package com.test.eventlogger.model.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.test.eventlogger.model.entity.EventLog;

@Repository
public interface EventLogRespository extends JpaRepository<EventLog, String> {}
