package io.tornadofaces.component.panel;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlPanelGroup;

@FacesComponent(value = Panel.COMPONENT_TYPE, createTag = true, tagName = "panelGroup", namespace = "http://tornadofaces.io/ui")
public class PanelGroup extends HtmlPanelGroup {

	public PanelGroup() {
		super();
		setRendererType(PanelGroupRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Boolean getEmpty() { return (Boolean) getStateHelper().eval("empty", false); }
	public void setEmpty(Boolean empty) { getStateHelper().put("empty", empty); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getTag() { return (String) getStateHelper().eval("tag", "div"); }
	public void setTag(String tag) { getStateHelper().put("tag", tag); }
	public String getEmptyClass() { return (String) getStateHelper().eval("emptyClass"); }
	public void setEmptyClass(String emptyClass) { getStateHelper().put("emptyClass", emptyClass); }
}
