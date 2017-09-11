package com.winhong.plugins.cicd.tool;

import java.security.SecureRandom;
import java.util.Locale;
import java.util.Random;

public class RandomString {

	public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

	public static final String lower = upper.toLowerCase(Locale.ROOT);

	public static final String digits = "0123456789";

	public static final String alphanum = upper + lower + digits;

	private static final Random random = new SecureRandom();

	private static char[] symbols = null;
	
	public static int Length=16;

	public static void init(int length) {
		init(length, alphanum);
	}

	public static void init(int length, String sym) {
		if (length < 1)
			throw new IllegalArgumentException();
		if (sym.length() < 2)
			throw new IllegalArgumentException();
		Length=length;
		symbols = sym.toCharArray();
	}

	/**
	 * Generate a random string.
	 */
	public static String nextString() {
		char[] buf = new char[Length];
		for (int idx = 0; idx < buf.length; ++idx)
			buf[idx] = symbols[random.nextInt(symbols.length)];
		return new String(buf);
	}

	/**
	 * Create an alphanumeric string generator.
	 */
	public RandomString(int length) {
		RandomString.init(length, alphanum);
	}

}