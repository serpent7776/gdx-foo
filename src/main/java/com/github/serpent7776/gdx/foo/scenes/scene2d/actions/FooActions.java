package com.github.serpent7776.gdx.foo.scenes.scene2d.actions;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;

public class FooActions extends Actions {

	public static MoveOriginToAction moveOriginTo(float x, float y, float duration) {
		final MoveOriginToAction action = action(MoveOriginToAction.class);
		action.setOrigin(x, y);
		action.setDuration(duration);
		return action;
	}

	public static AddActorAction addActorTo(Group parent) {
		final AddActorAction action = action(AddActorAction.class);
		action.setGroup(parent);
		return action;
	}

	public static InsertActorAction insertActorAt(int index, Group parent) {
		final InsertActorAction action = action(InsertActorAction.class);
		action.setGroup(parent);
		action.setIndex(index);
		return action;
	}

}
