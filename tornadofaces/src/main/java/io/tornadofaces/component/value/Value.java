package io.tornadofaces.component.value;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlInputHidden;
import java.util.Collection;
import java.util.Collections;

@ResourceDependency(library = "tornadofaces", name = "value.js")
@FacesComponent(value = "io.tornadofaces.component.Value", createTag = true, tagName = "value", namespace = "http://tornadofaces.io/ui")
public class Value extends HtmlInputHidden implements Widget, ClientBehaviorHolder {

	public Value() {
		super();
		setRendererType(ValueRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar", getName()); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getName() { return (String) getStateHelper().eval("name", getId()); }
	public void setName(String name) { getStateHelper().put("name", name); }

	public String getDefaultEventName() {
		return "valueChange";
	}

	public Collection<String> getEventNames() {
		return Collections.singletonList(getDefaultEventName());
	}
}
