package com.github.serpent7776.gdx.foo.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.Group;

public class InsertActorAction extends Action {

	private boolean added;
	private Group group;
	private int index;

	public void setGroup(Group parent) {
		group = parent;
	}

	public void setIndex(int idx) {
		index = idx;
	}

	@Override
	public boolean act(float delta) {
		if (!added) {
			added = true;
			group.addActorAt(index, target);
		}
		return true;
	}

	@Override
	public void restart() {
		added = false;
	}

}
