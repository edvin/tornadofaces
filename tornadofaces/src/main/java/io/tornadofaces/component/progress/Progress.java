package io.tornadofaces.component.progress;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(value = Progress.COMPONENT_TYPE, createTag = true, tagName = "progress", namespace = "http://tornadofaces.io/ui")
public class Progress extends UIComponentBase {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Progress";

	public enum Priority { high, medium, low }

	public Progress() {
		super();
		setRendererType(ProgressRenderer.RENDERER_TYPE);
	}

	public Integer getValue() { return (Integer) getStateHelper().eval("value", 0); }
	public void setValue(Integer value) { getStateHelper().put("value", value); }
	public Integer getMax() { return (Integer) getStateHelper().eval("max", 100); }
	public void setMax(Integer max) { getStateHelper().put("max", max); }
	public Priority getPriority() { return (Priority) getStateHelper().eval("priority"); }
	public void setPriority(Priority priority) { getStateHelper().put("priority", priority); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}
}
