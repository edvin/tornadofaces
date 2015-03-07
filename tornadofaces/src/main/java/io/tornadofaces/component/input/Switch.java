package io.tornadofaces.component.input;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.common.Size;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.event.FlipPanelEvent;
import io.tornadofaces.event.SwitchEvent;

import javax.el.ValueExpression;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.event.FacesEvent;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;

import static io.tornadofaces.component.util.ComponentUtils.isRequestSource;
import static io.tornadofaces.component.util.Constants.RequestParams.PARTIAL_BEHAVIOR_EVENT_PARAM;

@ResourceDependency(library = "tornadofaces", name = "switch.js")
@FacesComponent(value = Switch.COMPONENT_TYPE, createTag = true, tagName = "switch", namespace = "http://tornadofaces.io/ui")
public class Switch extends UIPanel implements Widget, ClientBehaviorHolder {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Switch";

	private HtmlSelectBooleanCheckbox checkbox;

	public Switch() {
		super();
		setRendererType(SwitchRenderer.RENDERER_TYPE);
		checkbox = new HtmlSelectBooleanCheckbox();
		getChildren().add(checkbox);
	}

	public String getDefaultEventName() {
		return "change";
	}

	public Collection<String> getEventNames() {
		return Collections.singletonList(getDefaultEventName());
	}

	public Size getSize() { return (Size) getStateHelper().eval("size", null); }
	public void setSize(Size size) { getStateHelper().put("size", size); }

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public ValueExpression getValueExpression(String name) {
		return checkbox.getValueExpression(name);
	}

	public void setValueExpression(String name, ValueExpression binding) {
		checkbox.setValueExpression(name, binding);
	}

	public HtmlSelectBooleanCheckbox getCheckbox() {
		return checkbox;
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getOnChange() { return (String) getStateHelper().eval("onChange"); }
	public void setOnChange(String onChange) { getStateHelper().put("onChange", onChange); }

	public void queueEvent(FacesEvent event) {
		FacesContext context = getFacesContext();

		if (isRequestSource(context, this) && event instanceof AjaxBehaviorEvent) {
			Map<String, String> params = context.getExternalContext().getRequestParameterMap();
			String eventName = params.get(PARTIAL_BEHAVIOR_EVENT_PARAM);
			AjaxBehaviorEvent behaviorEvent = (AjaxBehaviorEvent) event;

			if (eventName.equals("change")) {
				SwitchEvent switchEvent = new SwitchEvent(this, behaviorEvent.getBehavior());
				switchEvent.setPhaseId(behaviorEvent.getPhaseId());
				super.queueEvent(switchEvent);
			}
		} else {
			super.queueEvent(event);
		}
	}

	public boolean isChecked() {
		return checkbox.isSelected();
	}
}