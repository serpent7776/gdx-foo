package com.github.serpent7776.gdx.foo.utils;

import java.util.Arrays;

import org.testng.annotations.Test;
import static org.testng.Assert.*;

import com.badlogic.gdx.utils.Array;

public class RangesTest {

	@Test
	public void returnedArrayIsCorrectLength() {
		final int maxLength = 250;
		for (int i = 1; i < maxLength; i++) {
			final int[] array = Ranges.generatePermutedRange(i);
			assertEquals(array.length, i);
		}
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void throwsOnZeroLengthParameter() {
		Ranges.generatePermutedRange(0);
	}

	@Test(expectedExceptions=IllegalArgumentException.class)
	public void throwsOnNegativeLengthParameter() {
		Ranges.generatePermutedRange(-1);
	}

	@Test
	public void returnedArrayHasNoDuplicates() {
		final int maxLength = 250;
		for (int testNo = 1; testNo <= maxLength; testNo++) {
			final int[] array = Ranges.generatePermutedRange(testNo);
			for (int number = 0; number < testNo; number++) {
				int count = 0;
				for (int i = 0; i < testNo; i++) {
					if (array[i] == number) {
						count++;
					}
				}
				assertEquals(count, 1, "value " + number + " count is not 1. Array was " + Arrays.toString(array));
			}
		}
	}

}
