package com.overops.errors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.util.IndexOutOfBoundsErrorUtil;

public class IndexOutOfBounds implements BaseError {

	private final static Logger log = LoggerFactory.getLogger(IndexOutOfBounds.class);
	
	private void indexOutOfBoundsTest (int numberOfErrors) {	
		log.debug("Executing IndexOutOfBounds Errors");
		IndexOutOfBoundsErrorUtil.testErrors(numberOfErrors);
	}

	@Override
	public void executeError() {
		indexOutOfBoundsTest(10);	
	}
}
