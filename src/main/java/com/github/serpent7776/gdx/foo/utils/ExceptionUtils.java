package com.github.serpent7776.gdx.foo.utils;

import com.badlogic.gdx.utils.StringBuilder;

public final class ExceptionUtils {

	private ExceptionUtils() {
		// no instance
	}

	public static Throwable getRootCause(Throwable e) {
		Throwable cause = null;
		Throwable result = e;
		while ((cause = result.getCause()) != null  && (result != cause) ) {
			result = cause;
		}
		return result;
	}

	public static String getStackTrace(Throwable e) {
		final StringBuilder sb = new StringBuilder();
		while (e != null) {
			appendThrowable(sb, e);
			e = e.getCause();
		}
		return sb.toString();
	}

	/* private methods */

	private static void appendThrowable(StringBuilder sb, Throwable e) {
		sb.append(e.toString());
		sb.append('\n');
		final StackTraceElement[] elements = e.getStackTrace();
		for (StackTraceElement elem : elements) {
			sb.append(elem.toString());
			sb.append('\n');
		}
	}

}
