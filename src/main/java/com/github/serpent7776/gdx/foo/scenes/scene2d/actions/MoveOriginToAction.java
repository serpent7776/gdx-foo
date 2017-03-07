package com.github.serpent7776.gdx.foo.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.actions.TemporalAction;

public class MoveOriginToAction extends TemporalAction {

	private float startX;
	private float startY;
	private float endX;
	private float endY;

	public void setOrigin(float x, float y) {
		endX = x;
		endY = y;
	}

	@Override
	protected void begin() {
		startX = target.getOriginX();
		startY = target.getOriginY();
	}

	@Override
	protected void update(float percent) {
		target.setOrigin(startX + (endX - startX) * percent, startY + (endY - startY) * percent);
	}

}
