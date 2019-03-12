package com.test.eventlogger.service;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;

import com.test.eventlogger.service.impl.DurationImpl;

public class DurationTest {

	Duration duration = new DurationImpl();

	@Test
	public void testEvaluateEvent() {
		assertTrue( duration.evaluateEvent(1l, 2l) == 1l );
		assertTrue( duration.evaluateEvent(2l, 1l) == 1l );
		assertTrue( duration.evaluateEvent(1l, 1l) == 0l );
	}

	@Test
	public void testIsAlert() {
		assertTrue( duration.isAlert( 5l ) );
		assertFalse( duration.isAlert( 4l ) );
	}
}