package io.tornadofaces.component.input;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(value = FilterFor.COMPONENT_TYPE, createTag = true, tagName = "filterFor", namespace = ComponentUtils.NAMESPACE)
public class FilterFor extends UIComponentBase {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.FilterFor";

	public FilterFor() {
		super();
		setRendererType(FilterForRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}
	
	public String getWidget() { return (String) getStateHelper().eval("widget"); }
	public void setWidget(String widget) { getStateHelper().put("widget", widget); }
	public String getTarget() { return (String) getStateHelper().eval("target"); }
	public void setTarget(String target) { getStateHelper().put("target", target); }
}
