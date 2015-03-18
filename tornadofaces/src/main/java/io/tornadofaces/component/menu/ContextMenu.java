package io.tornadofaces.component.menu;

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
	public enum Position { above, auto }
	public ContextMenu() {
		super();
		setRendererType(ContextMenuRenderer.RENDERER_TYPE);
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getFor() { return (String) getStateHelper().eval("for"); }
	public void setFor(String _for) { getStateHelper().put("for", _for); }
	public Position getPosition() { return (Position) getStateHelper().eval("position", Position.auto); }
	public void setPosition(Position position) { getStateHelper().put("position", position); }
	public String getFilter() { return (String) getStateHelper().eval("filter"); }
	public void setFilter(String filter) { getStateHelper().put("filter", filter); }
	public Boolean getCompressed() { return (Boolean) getStateHelper().eval("compressed"); }
	public void setCompressed(Boolean compressed) { getStateHelper().put("compressed", compressed); }
	public Boolean getPreventdoublecontext() { return (Boolean) getStateHelper().eval("preventdoublecontext"); }
	public void setPreventdoublecontext(Boolean preventdoublecontext) { getStateHelper().put("preventdoublecontext", preventdoublecontext); }
	public Integer getFadespeed() { return (Integer) getStateHelper().eval("fadespeed", 100); }
	public void setFadespeed(Integer fadespeed) { getStateHelper().put("fadespeed", fadespeed); }
}
