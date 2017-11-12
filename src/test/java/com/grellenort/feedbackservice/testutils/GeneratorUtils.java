package com.grellenort.feedbackservice.testutils;

import java.util.Arrays;

public class GeneratorUtils {

	private GeneratorUtils() {
		// Util class does not init.
	}

	public static String generateLongString(final int size){
		char[] ar = new char[size];
		Arrays.fill(ar, 'a');
		return new String(ar);
	}
}
