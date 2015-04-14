package io.tornadofaces.component.flippanel;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.Constants;
import io.tornadofaces.event.FlipPanelEvent;
import io.tornadofaces.event.TabChangeEvent;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.AjaxBehaviorListener;
import javax.faces.event.FacesEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static io.tornadofaces.component.util.ComponentUtils.isRequestSource;
import static io.tornadofaces.component.util.Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "flippanel.js"),
	@ResourceDependency(library = "tornadofaces", name = "tweenmax.min.js")
})
@FacesComponent(value = FlipPanel.COMPONENT_TYPE, createTag = true, tagName = "flip-panel", namespace = "http://tornadofaces.io/ui")
public class FlipPanel extends UIPanel implements Widget, ClientBehaviorHolder {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.FlipPanel";

	public FlipPanel() {
		super();
		setRendererType(FlipPanelRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getDefaultEventName() {
		return "flip";
	}

	public Collection<String> getEventNames() {
		return Collections.singletonList(getDefaultEventName());
	}

	public void setBox(Boolean box) {
		getStateHelper().put("box", box);
	}

	public Boolean isBox() {
		return (Boolean) getStateHelper().eval("box", true);
	}

	public void setFlipped(Boolean flipped) {
		getStateHelper().put("flipped", flipped);
	}

	public Boolean isFlipped() {
		return (Boolean) getStateHelper().eval("flipped", false);
	}

	public void setMode(String mode) {
		getStateHelper().put("mode", mode);
	}

	public String getMode() {
		return (String) getStateHelper().eval("mode", "reverse");
	}

	public String getStyleClass() {
		return (String) getStateHelper().eval("styleClass");
	}

	public Integer getDuration() {
		return (Integer) getStateHelper().eval("duration", 275);
	}

	public void setDuration(Integer duration) {
		getStateHelper().put("duration", duration);
	}

	public void setStyleClass(String styleClass) {
		getStateHelper().put("styleClass", styleClass);
	}

	public String getStyle() {
		return (String) getStateHelper().eval("style");
	}

	public void setStyle(String style) {
		getStateHelper().put("style", style);
	}

	public String getWidgetVar() {
		return (String) getStateHelper().eval("widgetVar");
	}

	public void setWidgetVar(String widgetVar) {
		getStateHelper().put("widgetVar", widgetVar);
	}

	public String getOnFlip() {
		return (String) getStateHelper().eval("onFlip");
	}

	public void setOnFlip(String onFlip) {
		getStateHelper().put("onFlip", onFlip);
	}

	public void queueEvent(FacesEvent event) {
		FacesContext context = getFacesContext();

		if (isRequestSource(context, this) && event instanceof AjaxBehaviorEvent) {
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			String eventName = params.get(PARTIAL_BEHAVIOR_EVENT_PARAM);
			AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

			if (eventName.equals("flip")) {
				FlipPanelEvent flipPanelEvent = new FlipPanelEvent(this, behaviorEvent.getBehavior());
				flipPanelEvent.setPhaseId(behaviorEvent.getPhaseId());
				super.queueEvent(flipPanelEvent);
			}
		} else {
			super.queueEvent(event);
		}
	}
}

