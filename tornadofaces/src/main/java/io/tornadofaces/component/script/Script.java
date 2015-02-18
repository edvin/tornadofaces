package io.tornadofaces.component.script;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(createTag = true, tagName = "script", namespace = "http://tornadofaces.io/ui")
public class Script extends UIComponentBase {
	
	public Script() {
		super();
		setRendererType(ScriptRenderer.RENDERER_TYPE);
	}
	
	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getValue() { return (String) getStateHelper().eval("value"); }
	public void setValue(String value) { getStateHelper().put("value", value); }
}
