package com.test.eventlogger.service;

import org.springframework.stereotype.Service;

@Service
public interface Duration {

	/**
	 * Evaluate the event duration
	 * @param existinTimeStamp
	 * @param newTimeStamp
	 * @return
	 */
	public Long evaluateEvent( Long existinTimeStamp, Long newTimeStamp );

	/**
	 * 
	 * @param duration
	 * @return
	 */
	public Boolean isAlert( Long duration );
}
