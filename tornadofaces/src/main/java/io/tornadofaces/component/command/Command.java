package io.tornadofaces.component.command;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UICommand;
import javax.faces.component.behavior.ClientBehaviorHolder;

@ResourceDependency(library = "tornadofaces", name = "command.js")
@FacesComponent(value = "io.tornadofaces.component.Command", createTag = true, tagName = "command", namespace = "http://tornadofaces.io/ui")
public class Command extends UICommand implements Widget, ClientBehaviorHolder {

	public Command() {
		super();
		setRendererType(CommandRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Boolean getOnload() { return (Boolean) getStateHelper().eval("onload", false); }
	public void setOnload(Boolean onload) { getStateHelper().put("onload", onload); }
	public String getName() { return (String) getStateHelper().eval("name"); }
	public void setName(String name) { getStateHelper().put("name", name); }

	public String getOnsuccess() { return (String) getStateHelper().eval("onsuccess"); }
	public void setOnsuccess(String onsuccess) { getStateHelper().put("onsuccess", onsuccess); }

	public String getOncomplete() { return (String) getStateHelper().eval("oncomplete"); }
	public void setOncomplete(String oncomplete) { getStateHelper().put("oncomplete", oncomplete); }

	public String getOnstart() { return (String) getStateHelper().eval("onstart"); }
	public void setOnstart(String onstart) { getStateHelper().put("onstart", onstart); }

	public String getExecute() { return (String) getStateHelper().eval("execute", "@form"); }
	public void setExecute(String execute) {
		getStateHelper().put("execute", execute);
	}

	public String getRender() {
		return (String) getStateHelper().eval("render", "@form");
	}
	public void setRender(String render) {
		getStateHelper().put("render", render);
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
}
