package com.test.eventlogger.impl;

import java.io.File;
import java.io.IOException;
import java.util.Optional;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.test.eventlogger.EventProcessor;
import com.test.eventlogger.model.YesNo;
import com.test.eventlogger.model.entity.EventLog;
import com.test.eventlogger.model.json.Event;
import com.test.eventlogger.model.repo.EventLogRespository;
import com.test.eventlogger.service.Duration;

@Service
public class EventProcessorImpl implements EventProcessor {

	static final Logger logger = LoggerFactory.getLogger(EventProcessorImpl.class);

	@Autowired
	private EventLogRespository eventLogRespository;

	@Autowired
	private Duration duaration;

	/*
	 * (non-Javadoc)
	 * @see com.test.eventlogger.EventProcessor#processFile(java.lang.String)
	 */
	@Override
	public void processFile(String fileName) {

		try ( LineIterator it = FileUtils.lineIterator( new File( fileName ), "UTF-8" ); ) {
			Gson gson = new GsonBuilder().create();
			while ( it.hasNext() ) {
				String line = it.nextLine();
				// Convert line to json object
				Event event = gson.fromJson( line, Event.class );
				processEvent( event );
			}
		} catch ( IOException e ) {
			logger.error( e.getMessage() );
		}

	}

	/**
	 * Process Event
	 * @param event
	 */
	@Transactional
	void processEvent( Event event ) {

		// Find an event with the id
		Optional<EventLog>existingEventLog = eventLogRespository.findById( event.getId() );

		if( existingEventLog.isPresent() ) {
			logger.debug( "Event found {}", event.getId() );
			// update the event with the second time stamp
			EventLog existingEventLogEntity = existingEventLog.get();

			// Evaluate the event duration
			Long duration = duaration.evaluateEvent( existingEventLogEntity.getEventDuration(), Long.valueOf( event.getTimestamp() ) );
			existingEventLogEntity.setEventDuration( duration );
			logger.debug( "New Duration value {}", duration );

			// Update Alert
			Boolean alert = duaration.isAlert( duration );
			existingEventLogEntity.setAlert( YesNo.getEnumValue( alert ).name() );
			logger.debug( "Alert: {}", alert );

			// update existing event
			eventLogRespository.save( existingEventLogEntity );

			logger.debug( "Event Log updated {}", existingEventLogEntity.getEventId() );
		} else {

			logger.debug( "New Event {}", event.getId() );

			EventLog newEventLog = new EventLog();
			newEventLog.setEventId( event.getId() );
			newEventLog.setEventDuration( Long.valueOf( event.getTimestamp() ) );
			// initially no alert 
			newEventLog.setAlert( YesNo.N.name() );
			newEventLog.setEventType( event.getType() );
			newEventLog.setHost( event.getHost() ); 
			// create new event
			eventLogRespository.save( newEventLog );

			logger.debug( "Event Log created {}", newEventLog.getEventId() );
		}
	}
}