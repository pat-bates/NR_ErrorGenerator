package com.overops.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.HashMap;

import com.newrelic.api.agent.NewRelic;
import com.google.common.collect.ImmutableMap;

// import com.overops.util.ErrorLogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ErrorSequenceUtil {
	private final static Logger log = LoggerFactory.getLogger(ErrorSequenceUtil.class);

	
	public static void testErrors () {
	
		sleep();
		testErrorSequence();

	}

	private static void testHandledNullPointer() {

		String strValue = null;

                /* generate a handled NullPointerException */
                try {
                        if (strValue.equalsIgnoreCase("hello")) {
                        }
                } catch (Exception e) {
                        log.error(RandomUtil.convertStackTraceToString(e));
                        NewRelic.noticeError(e, ImmutableMap.of("strValue", "null"), false);
                }

		sleep();
	}

	private static void testHandledNumberFormat() {

                String numValue = "One";

                /* generate a handled NumberFormatException */
                try {
                        Integer.parseInt(numValue);
                } catch (NumberFormatException e) {
                        log.error(RandomUtil.convertStackTraceToString(e));
                        NewRelic.noticeError(e, ImmutableMap.of("numValue", numValue), false);
                        // NewRelic.noticeError(e, false);
                }

		sleep();
	}

	private static void testHandledParseException() {

                String dateValue = "100-100-1100";

                /* generate a ParseException */
                try {
                        long value = RandomUtil.convertDate(dateValue);
                } catch (Exception e) {
                        log.error("Error converting date, " + RandomUtil.convertStackTraceToString(e));
                        NewRelic.noticeError(e, ImmutableMap.of("dateValue", dateValue), false);
                }

		sleep();
	}

	private static void testLoggedError() {
		String errorMsg = "This is a logged error message";
		log.error(errorMsg);
		NewRelic.noticeError(errorMsg, ImmutableMap.of("eMsg", errorMsg), false);
		// NewRelic.noticeError(errorMsg, false);

		sleep();
	}

	private static void testUnhandledException() {

		Object obj = new Integer(100);

                /* generate an unhandled exception */
		String strObj = (String)obj;
	}

	/*
	 * This creates a sequence of caught then an uncaught error
	 */
	private static void testErrorSequence () {

		testHandledNullPointer();
		testHandledNumberFormat();
		testHandledParseException();
		testLoggedError();
		testUnhandledException();

	}
	
	private static void sleep () {
		try {
			Thread.sleep(1000);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	
}
