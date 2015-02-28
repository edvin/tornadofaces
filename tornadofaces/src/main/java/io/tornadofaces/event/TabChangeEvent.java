package io.tornadofaces.event;

import io.tornadofaces.component.tab.Tab;
import lombok.Getter;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;

public class TabChangeEvent extends AjaxBehaviorEvent {
	@Getter private Tab tab;

	public TabChangeEvent(UIComponent component, Behavior behavior, Tab tab) {
		super(component, behavior);
		this.tab = tab;
	}
}
