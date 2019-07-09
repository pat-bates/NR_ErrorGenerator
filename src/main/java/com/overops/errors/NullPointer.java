package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.NullErrorUtil;

public class NullPointer implements BaseError {

	private final static Logger log = LoggerFactory.getLogger(NullPointer.class);
	
	private void nullPointerTest (int numberOfErrors) {	
		log.debug("Executing NullPointer Errors");
		NullErrorUtil.testErrors(numberOfErrors);
	}

	@Override
	public void executeError() {
		nullPointerTest(10);	
	}
}
