package io.tornadofaces.component.slider;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@FacesComponent(value = SliderHeader.COMPONENT_TYPE, createTag = true, tagName = "slider-header", namespace = "http://tornadofaces.io/ui")
public class SliderHeader extends UIPanel {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.SliderHeader";

	public SliderHeader() {
		super();
		setRendererType(SliderHeaderRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getValue() {
		return (String) getStateHelper().eval("value", "");
	}
	public void setValue(String value) {
		getStateHelper().put("value", value);
	}
	public String getMin() {
		return (String) getStateHelper().eval("min", "");
	}
	public void setMin(String min) {
		getStateHelper().put("min", min);
	}
	public String getMax() {
		return (String) getStateHelper().eval("max", "");
	}
	public void setMax(String max) {
		getStateHelper().put("max", max);
	}

}
