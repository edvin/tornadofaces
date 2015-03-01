package io.tornadofaces.event;

import io.tornadofaces.component.input.Switch;

import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;

public class SwitchEvent extends AjaxBehaviorEvent {

	public SwitchEvent(Switch _switch, Behavior behavior) {
		super(_switch, behavior);
	}
	
	public Switch getSwitch() {
		return (Switch) getComponent();
	}
}