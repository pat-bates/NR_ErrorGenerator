package com.overops;

import com.overops.util.NullErrorUtil;

public class NullPointerMain {


	public static void main(String[] args) {
		Integer numOfErrors = Integer.parseInt(args.length == 0 ? "1000": args[0]);
		try {
			//Sleep for 10 seconds to allow OverOps to warm up
			Thread.sleep(10000);
		} catch (Exception e) {
			e.printStackTrace();
		}
		nullPointerTest(numOfErrors);
	}
	
	/*
	 * call the CreateError class and test the null code
	 */
	private static void nullPointerTest (int numberOfErrors) {	
		NullErrorUtil.testErrors(numberOfErrors);
	}
}
