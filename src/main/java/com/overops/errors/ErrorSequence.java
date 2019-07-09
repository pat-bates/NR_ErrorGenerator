package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.ErrorSequenceUtil;

public class ErrorSequence implements BaseError {

	private final static Logger log = LoggerFactory.getLogger(ErrorSequence.class);
	
	private void errorSequenceTest () {	
		log.debug("Executing ErrorSequence Errors");
		ErrorSequenceUtil.testErrors();
	}

	@Override
	public void executeError() {
		errorSequenceTest();	
	}
}
