package io.tornadofaces.component.notification;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIMessages;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "jquery.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.js"),
	@ResourceDependency(library = "tornadofaces", name = "foundation.css"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.css"),
	@ResourceDependency(library = "tornadofaces", name = "notification.js")
})
@FacesComponent(value = Notification.COMPONENT_TYPE, createTag = true, tagName = "notification", namespace = "http://tornadofaces.io/ui")
public class Notification extends UIMessages implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Notification";

	public Notification() {
		super();
		setRendererType(NotificationRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public void setTimeout(Integer timeout) {
		getStateHelper().put("timeout", timeout);
	}
	public Integer getTimeout() {
		return (Integer) getStateHelper().eval("timeout", 5000);
	}

	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }

	public void setPosition(String position) { getStateHelper().put("position", position); }
	public String getPosition() { return (String) getStateHelper().eval("position", "top-right"); }

	public Boolean isClosable() { return (Boolean) getStateHelper().eval("closable", true); }
	public void setClosable(Boolean closable) { getStateHelper().put("closable", closable); }
}