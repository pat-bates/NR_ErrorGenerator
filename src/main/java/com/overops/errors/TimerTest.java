package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.TimerTestUtil;

public class TimerTest implements BaseError {

	private final static Logger log = LoggerFactory.getLogger(TimerTest.class);
	
	private void timerTest (int numberOfErrors) {	
		log.debug("Executing Timer Test");
		TimerTestUtil.testErrors(numberOfErrors);
	}

	@Override
	public void executeError() {
		timerTest(10);	
	}
}
