package com.github.serpent7776.gdx.foo.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;

public class AddActorAction extends Action {

	private boolean added;
	private Group group;

	public void setGroup(Group parent) {
		group = parent;
	}

	@Override
	public boolean act(float delta) {
		if (!added) {
			added = true;
			group.addActor(target);
		}
		return true;
	}

	@Override
	public void restart() {
		added = false;
	}

}
