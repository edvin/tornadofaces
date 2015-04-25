package io.tornadofaces.component.form;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlForm;

@FacesComponent(value = Form.COMPONENT_TYPE, createTag = true, tagName = "form", namespace = "http://tornadofaces.io/ui")
public class Form extends HtmlForm {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Form";

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Boolean getShow() { return (Boolean) getStateHelper().eval("show", true); }
	public void setShow(Boolean show) { getStateHelper().put("show", show); }
}
