package com.test.eventlogger.service.impl;

import org.springframework.stereotype.Service;

import com.test.eventlogger.service.Duration;

@Service
public class DurationImpl implements Duration {

	private final static Long ALERT_TRESHOLD = 4l;

	/*
	 * (non-Javadoc)
	 * @see com.test.eventlogger.service.Duration#evaluateEvent(java.lang.Long, java.lang.Long)
	 */
	public Long evaluateEvent( Long existinTimeStamp, Long newTimeStamp ) {
		Long result = existinTimeStamp - newTimeStamp;
		// absolute value, as no guarantee that the existing time stamp is the start
		return Math.abs( result );
	}

	/*
	 * (non-Javadoc)
	 * @see com.test.eventlogger.service.Duration#isAlert(java.lang.Long)
	 */
	@Override
	public Boolean isAlert( Long duration ) {
		return duration > ALERT_TRESHOLD;
	}
}
