package com.github.serpent7776.gdx.foo.utils;

import com.badlogic.gdx.math.MathUtils;

public class Ranges {

	/**
	 * Generates array of permuted values in range [0; length - 1].
	 * @Param length Length of returned array. Must be greater than zero.
	 * @Return Array of ints with values in range [0; length-1] and permuted.
	 */
	public static int[] generatePermutedRange(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("length must be positive");
		}
		final int[] array = new int[length];
		// use "inside-out" Fisher–Yates shuffle
		for (int i = 0; i < length; i++) {
			final int j = MathUtils.random.nextInt(i + 1);
			if (i != j) {
				array[i] = array[j];
			}
			array[j] = i;
		}
		return array;
	}

}
