package com.overops;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.overops.errors.BaseError;
import com.overops.util.RandomUtil;

import com.newrelic.api.agent.NewRelic;

public class ErrorGenerator {
	private final static Logger log = LoggerFactory.getLogger(ErrorGenerator.class);
	private static Map<String, String> classMap = new HashMap<String, String>();
	private static List<String> classList = new ArrayList<String>();
		
	/**
	 * runTime is the length of time you want the error generator to run in minutes
	 * errorType options are "all", "NP" for null pointer, "CCE" for class cast exception, "DP" for date parsing errors
	 * @param args
	 */
	public static void main(String[] args) {
		Integer runTime = Integer.parseInt(args.length == 0 ? "10": args[0]);
		String errorType = args.length == 0 ? "DP": args[1];

		if (errorType.equalsIgnoreCase("seq")) {
			generateErrorSequence(runTime);
		}
		else {
			setupClasses();
			warmUp();
			classList = setupErrors(errorType);
			generateErrors(runTime, errorType);	
		}
	}
	
	/**
	 * Execute a single fixed sequence of errors simply to validate monitoring
	 */
	@SuppressWarnings("unchecked")
	private static void generateErrorSequence(Integer runTime) {

		try {
			// Thread.sleep(120000);
		} catch (Exception ex) {
                	log.error(RandomUtil.convertStackTraceToString(ex));
		}

		for (int i = 0; i < runTime; i++) {
			try {
                		Class<BaseError> cls = (Class<BaseError>) Class.forName("com.overops.errors.ErrorSequence");
                		Object classInstance = cls.newInstance();
                		Method method = cls.getMethod("executeError", null);
                		method.invoke(classInstance, null);
        		} catch (ClassNotFoundException e) {
                		log.error("Class {} not found", "com.overops.errors.ErrorSequence");
        		} catch (NoSuchMethodException ex) {
                		log.error("{} method not found", "executeError");
        		} catch (InvocationTargetException ex) {
                		log.error(RandomUtil.convertStackTraceToString(ex));
        		} catch (IllegalAccessException ex) {
                		log.error(RandomUtil.convertStackTraceToString(ex));
        		} catch (InstantiationException ex) {
                		log.error(RandomUtil.convertStackTraceToString(ex));
			}
		} // end for

                /* generate an unhandled exception */
		Object obj = new Integer(100);
                String strObj = (String)obj;
	}

	/**
	 * Execute errors for a given length of time
	 * @param runTime
	 */
	@SuppressWarnings("unchecked")
	private static void generateErrors(Integer runTime, String errorType) {
		Long endTime = System.currentTimeMillis() + (runTime*1000*60);
		log.info("Executing error generator for Error Type {} starting at {} for {} minute(s)", errorType, java.time.LocalDateTime.now(), runTime);
		
		while (System.currentTimeMillis() < endTime) {
			for (@SuppressWarnings("rawtypes")
			Iterator iterator = classList.iterator(); iterator.hasNext();) {
				String string = (String) iterator.next();
				try {
					log.debug("Find class for name: {}", string);
					Class<BaseError> cls = (Class<BaseError>) Class.forName(string);
					Object classInstance = cls.newInstance();
					Method method = cls.getMethod("executeError", null);
					method.invoke(classInstance, null);
				} catch (ClassNotFoundException e) {
					log.error("Class {} not found", string);
				} catch (NoSuchMethodException ex) {
					log.error("{} method not found", "executeError");
				} catch (InvocationTargetException ex) {
					log.error(RandomUtil.convertStackTraceToString(ex));
				} catch (IllegalAccessException ex) {
					log.error(RandomUtil.convertStackTraceToString(ex));
				} catch (InstantiationException ex) {
					log.error(RandomUtil.convertStackTraceToString(ex));
				} 
			}	
		}
		
		//give the JVM some time to finish up before shutting down
		warmUp();
		log.info("Exectution complete - end time {}", java.time.LocalDateTime.now());
	}
	
	/**
	 * setup the classes used for error generation
	 * @param errorType
	 * @return
	 */
	private static List<String> setupErrors(String errorType) {
		List<String> classList = null;
		
		if (errorType.equalsIgnoreCase("all")) { 
			classList = new ArrayList<String>(classMap.values());
		} else  {
			classList = new ArrayList<String>();
			classList.add(classMap.get(errorType));
		} 
		
		return classList;
	}
	
	/**
	 * need 10 seconds for OverOps to warm up before starting to generate errors
	 */
	private static void warmUp() {
		log.debug("Wait 10 seconds for OverOps to connect to collector and get ready to insert tiny link");
		try {
			//Sleep for 10 seconds to allow OverOps to warm up
			Thread.sleep(10000);
		} catch (Exception e) {
			log.error(RandomUtil.convertStackTraceToString(e));
		}
	}
	
	/**
	 * list of classes used for error testing
	 */
	private static void setupClasses() {
		log.debug("Setting up classes for Error Engine");
		classMap.put("NP", "com.overops.errors.NullPointer");
		classMap.put("DP", "com.overops.errors.DateParsingError");
		classMap.put("IOOB", "com.overops.errors.IndexOutOfBounds");
		classMap.put("CCE", "com.overops.errors.InvalidCastException");
		classMap.put("IA", "com.overops.errors.InvalidArgument");
		classMap.put("NFE", "com.overops.errors.NumberFormatException");
		classMap.put("TT", "com.overops.errors.TimerTest");
		classMap.put("UK", "com.overops.errors.IllegalError");
	}
}
