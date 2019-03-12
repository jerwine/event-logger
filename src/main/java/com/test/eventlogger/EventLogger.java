package com.test.eventlogger;

import org.springframework.stereotype.Service;

@Service
public interface EventLogger {

	public void processFiles( String... files );

}