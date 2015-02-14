package io.tornadofaces.component.panel;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.common.Position;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "jquery.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.js"),
	@ResourceDependency(library = "tornadofaces", name = "foundation.css"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.css"),
	@ResourceDependency(library = "tornadofaces", name = "panel.js")
})
@FacesComponent(value = Panel.COMPONENT_TYPE, createTag = true, tagName = "panel", namespace = "http://tornadofaces.io/ui")
public class Panel extends UIPanel implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Panel";

	public Panel() {
		super();
		setRendererType(PanelRenderer.RENDERER_TYPE);
	}


	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public Position getPosition() { return (Position) getStateHelper().eval("position", Position.top); }
	public void setPosition(Position position) { getStateHelper().put("position", position); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public Boolean getOpen() { return (Boolean) getStateHelper().eval("open", false); }
	public void setOpen(Boolean open) { getStateHelper().put("open", open); }
	public Boolean getClosable() { return (Boolean) getStateHelper().eval("closable", true); }
	public void setClosable(Boolean closable) { getStateHelper().put("closable", closable); }
}