package io.tornadofaces.component.util;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(value = Focus.COMPONENT_TYPE, createTag = true, tagName = "focus", namespace = ComponentUtils.NAMESPACE)
public class Focus extends UIComponentBase {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Focus";

	public Focus() {
		super();
		setRendererType(FocusRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getTarget() { return (String) getStateHelper().eval("target"); }
	public void setTarget(String target) { getStateHelper().put("target", target); }
	public Boolean getRemove() { return (Boolean) getStateHelper().eval("remove", true); }
	public void setRemove(Boolean remove) { getStateHelper().put("remove", remove); }

}
