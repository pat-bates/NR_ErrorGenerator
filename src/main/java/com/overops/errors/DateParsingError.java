package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.DateParsingErrorUtil;

public class DateParsingError implements BaseError {
	private final static Logger log = LoggerFactory.getLogger(DateParsingError.class);
	
	@Override
	public void executeError() {
		log.debug("Executing Date Parsing Error");
		dateParsingErrorTest(10);
	}
	
	private void dateParsingErrorTest (int numberOfErrors) {	
		DateParsingErrorUtil.testErrors(numberOfErrors);
	}

}
