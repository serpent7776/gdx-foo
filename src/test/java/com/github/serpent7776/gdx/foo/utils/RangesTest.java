package com.github.serpent7776.gdx.foo.utils;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

public class RangesTest {

	@Test
	public void returnedArrayIsCorrectLength() {
		final int maxLength = 250;
		for (int i = 1; i < maxLength; i++) {
			final int[] array = Ranges.generatePermutedRange(i);
			assertEquals(array.length, i);
		}
	}
}
