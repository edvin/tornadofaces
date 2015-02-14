package io.tornadofaces.event;

import io.tornadofaces.component.tab.Tab;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;

public class TabChangeEvent extends AjaxBehaviorEvent {
	private Tab tab;

	public TabChangeEvent(UIComponent component, Behavior behavior, Tab tab) {
		super(component, behavior);
		this.tab = tab;
	}

	public Tab getTab() {
		return tab;
	}
}
