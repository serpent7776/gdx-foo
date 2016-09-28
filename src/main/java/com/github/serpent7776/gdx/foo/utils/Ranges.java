package com.github.serpent7776.gdx.foo.utils;

import com.badlogic.gdx.math.MathUtils;

public class Ranges {

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
