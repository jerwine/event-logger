package com.test.eventlogger.model.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table( name="event_log", schema = "PUBLIC" )
public class EventLog {

	@Id
	@Column( name = "event_id" )
	private String eventId;

	@Column( name = "event_duration", nullable = false )
	private Long eventDuration;

	@Column( name = "event_type", nullable = true )
	private String eventType;

	@Column( name = "host", nullable = true )
	private String host;

	@Column( name = "alert", nullable = true )
	private String alert;

	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	public Long getEventDuration() {
		return eventDuration;
	}
	public void setEventDuration(Long eventDuration) {
		this.eventDuration = eventDuration;
	}
	public String getEventType() {
		return eventType;
	}
	public void setEventType(String eventType) {
		this.eventType = eventType;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getAlert() {
		return alert;
	}
	public void setAlert(String alert) {
		this.alert = alert;
	}

}
