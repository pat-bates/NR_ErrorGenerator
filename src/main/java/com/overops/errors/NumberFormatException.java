package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.NumberFormatUtil;

public class NumberFormatException implements BaseError {

	private final static Logger log = LoggerFactory.getLogger(NumberFormatException.class);
	
	private void classCastExceptionTest (int numberOfErrors) {	
		log.debug("Executing NumberFormatException Errors");
		NumberFormatUtil.testErrors(numberOfErrors);
	}

	@Override
	public void executeError() {
		classCastExceptionTest(10);	
	}
}
