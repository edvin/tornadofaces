package io.tornadofaces.component.contextmenu;

import io.tornadofaces.component.api.Widget;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "context.js"),
	@ResourceDependency(library = "tornadofaces", name = "contextmenu.js"),
})
@FacesComponent(value = ContextMenu.COMPONENT_TYPE, createTag = true, tagName = "context-menu", namespace = "http://tornadofaces.io/ui")
public class ContextMenu extends UIPanel implements Widget {

	public ContextMenu() {
		super();
		setRendererType(ContextMenuRenderer.RENDERER_TYPE);
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public Boolean getCompressed() { return (Boolean) getStateHelper().eval("compressed"); }
	public void setCompressed(Boolean compressed) { getStateHelper().put("compressed", compressed); }
}
