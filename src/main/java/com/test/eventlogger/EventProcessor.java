package com.test.eventlogger;

public interface EventProcessor {

	/**
	 * Process the log file
	 * @param fileName
	 */
	public void processFile( String fileName );

}
