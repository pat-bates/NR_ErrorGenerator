package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.InvalidCastExceptionUtil;

public class InvalidCastException implements BaseError {

	private final static Logger log = LoggerFactory.getLogger(InvalidCastException.class);
	
	private void classCastExceptionTest (int numberOfErrors) {	
		log.debug("Executing InvalidCastException Errors");
		InvalidCastExceptionUtil.testErrors(numberOfErrors);
	}

	@Override
	public void executeError() {
		classCastExceptionTest(10);	
	}
}
