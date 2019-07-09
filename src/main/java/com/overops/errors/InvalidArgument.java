package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.InvalidArgumentUtil;

public class InvalidArgument implements BaseError {
	
	private final static Logger log = LoggerFactory.getLogger(InvalidArgument.class);
	
	@Override
	public void executeError() {
		log.debug("Executing Date Parsing Error");
		dateParsingErrorTest(10);
	}
	
	private void dateParsingErrorTest (int numberOfErrors) {	
		InvalidArgumentUtil.testErrors(numberOfErrors);
	}

}
