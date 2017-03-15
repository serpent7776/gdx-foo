package com.github.serpent7776.gdx.foo.utils;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class ExceptionUtilsTest {

	@Test
	public void getRootCauseReturnSelfWhenNoCause() {
		final Throwable e = new Throwable("test");
		final Throwable rootCause = ExceptionUtils.getRootCause(e);
		assertEquals(rootCause, e);
	}

	@Test
	public void getRootCauseReturnCause() {
		final Throwable cause = new Throwable("test cause");
		final Throwable e = new Throwable("test", cause);
		final Throwable rootCause = ExceptionUtils.getRootCause(e);
		assertEquals(rootCause, cause);
	}

	@Test
	public void getRootCauseReturnRootCause() {
		final Throwable cause = new Throwable("test cause");
		final Throwable inner1 = new Throwable("inner exception", cause);
		final Throwable inner2 = new Throwable("inner exception", inner1);
		final Throwable inner3 = new Throwable("inner exception", inner2);
		final Throwable e = new Throwable("test", inner3);
		final Throwable rootCause = ExceptionUtils.getRootCause(e);
		assertEquals(rootCause, cause);
	}

}
