package io.tornadofaces.component.data;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js"),
	@ResourceDependency(library = "tornadofaces", name = "jquery.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.js"),
	@ResourceDependency(library = "tornadofaces", name = "data.js")
})
@FacesComponent(value = Data.COMPONENT_TYPE, createTag = true, tagName = "data", namespace = "http://tornadofaces.io/ui")
public class Data extends UIComponentBase implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Data";

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getVar() { return (String) getStateHelper().eval("var"); }
	public void setVar(String var) { getStateHelper().put("var", var); }
	public String getSuccess() { return (String) getStateHelper().eval("success"); }
	public void setSuccess(String success) { getStateHelper().put("success", success); }
	public Object getValue() { return getStateHelper().eval("value"); }
	public void setValue(Object value) { getStateHelper().put("value", value); }
	public Boolean getLazy() { return (Boolean) getStateHelper().eval("lazy", false); }
	public void setLazy(Boolean lazy) { getStateHelper().put("lazy", lazy); }
	public Boolean getJson() { return (Boolean) getStateHelper().eval("json", true); }
	public void setJson(Boolean json) { getStateHelper().put("json", json); }
}