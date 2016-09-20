package com.github.serpent7776.gdx.foo.utils;

import com.badlogic.gdx.math.MathUtils;

public class Ranges {

	public static int[] generatePermutedRange(int length) {
		if (length <= 0) {
			throw new IllegalArgumentException("length must be positive");
		}
		int[] array = new int[length];
		// create ordered array
		for (int i = 0; i < length; i++) {
			array[i] = i;
		}
		// randomize elements
		for (int i = array.length - 1; i > 0; i--) {
			final int index = MathUtils.random.nextInt(i + 1);
			// Simple swap
			if (i != index) {
				final int temp = array[index];
				array[index] = array[i];
				array[i] = temp;
			}
		}
		return array;
	}

}
