package io.tornadofaces.event;

import lombok.Getter;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;

public class SelectionEvent<T> extends AjaxBehaviorEvent {
	@Getter private T selection;

	public SelectionEvent(UIComponent component, Behavior behavior, T selection) {
		super(component, behavior);
		this.selection = selection;
	}
}