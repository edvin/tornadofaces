package io.tornadofaces.component.flippanel;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependency(library = "tornadofaces", name = "flippanel.js")
@FacesComponent(value = FlipPanel.COMPONENT_TYPE, createTag = true, tagName = "flip-panel", namespace = "http://tornadofaces.io/ui")
public class FlipPanel extends UIPanel implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.FlipPanel";

	public FlipPanel() {
		super();
		setRendererType(FlipPanelRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public void setBox(Boolean box) { getStateHelper().put("box", box); }
	public Boolean isBox() { return (Boolean) getStateHelper().eval("box", true); }
	public void setFlipped(Boolean flipped) { getStateHelper().put("flipped", flipped); }
	public Boolean isFlipped() { return (Boolean) getStateHelper().eval("flipped", true); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getOnFlip() { return (String) getStateHelper().eval("onFlip"); }
	public void setOnFlip(String onFlip) { getStateHelper().put("onFlip", onFlip); }
}

