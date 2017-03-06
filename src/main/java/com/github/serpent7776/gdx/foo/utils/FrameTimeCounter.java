package com.github.serpent7776.gdx.foo.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.FloatCounter;

public final class FrameTimeCounter {

	private static final String TAG = FrameTimeCounter.class.getSimpleName();

	private final FloatCounter counter;
	private long t0;

	public FrameTimeCounter(int windowSize) {
		this.counter = new FloatCounter(windowSize);
	}

	public void begin() {
		t0 = System.nanoTime();
	}

	public float end() {
		final float value = (System.nanoTime() - t0) / 1e6f;
		counter.put(value);
		return value;
	}

	public String toString() {
		return "min: " + counter.min + ", max: " + counter.max + ", avg: " + counter.average + ", mean: " + counter.value;
	}

	public void log() {
		Gdx.app.log(TAG, toString());
	}

}
