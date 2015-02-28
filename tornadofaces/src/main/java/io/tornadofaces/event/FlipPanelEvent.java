package io.tornadofaces.event;

import io.tornadofaces.component.flippanel.FlipPanel;
import lombok.Getter;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.Behavior;
import javax.faces.event.AjaxBehaviorEvent;

public class FlipPanelEvent extends AjaxBehaviorEvent {

	public FlipPanelEvent(FlipPanel panel, Behavior behavior) {
		super(panel, behavior);
	}
	
	public FlipPanel getFlipPanel() {
		return (FlipPanel) getComponent();
	}
}