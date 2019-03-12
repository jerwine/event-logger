package com.test.eventlogger.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.eventlogger.EventLogger;
import com.test.eventlogger.EventProcessor;

@Service
public class EventLoggerImpl implements EventLogger {

	static final Logger logger = LoggerFactory.getLogger(EventLoggerImpl.class);

	@Autowired
	EventProcessor eventProcessor;

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.test.eventlogger.EventLogger#processFiles(java.lang.String[])
	 */
	@Override
	public void processFiles( String... files ) {

		// Create a thread pool, the size of the pool is determined by the number of input files 
		ExecutorService executorService = Executors.newFixedThreadPool( files.length );

		// List of tasks
		List<Callable<String>> callableTasks = new ArrayList<>();

		// Process each file in a separate thread
		for ( String file : files ) {

			logger.info( file );

			// For every file, create a Callable task
			Callable<String> callableTask = () -> {
				// Process the file in the new thread
				eventProcessor.processFile( file );
				return file;
			};

			callableTasks.add( callableTask );
		}

		try {
			// start all the threads
			executorService.invokeAll( callableTasks );
		} catch ( InterruptedException e ) {
			logger.error( e.getMessage() );
		} finally {
			// close service
			executorService.shutdown();
		}
	}
}