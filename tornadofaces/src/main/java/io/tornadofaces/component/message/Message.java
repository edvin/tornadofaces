package io.tornadofaces.component.message;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIMessages;

@ResourceDependency(library = "tornadofaces", name = "message.js")
@FacesComponent(value = Message.COMPONENT_TYPE, createTag = true, tagName = "notification", namespace = "http://tornadofaces.io/ui")
public class Message extends UIMessages implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Message";

	public Message() {
		super();
		setRendererType(MessageRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public void setTimeout(Integer timeout) {
		getStateHelper().put("timeout", timeout);
	}
	public Integer getTimeout() {
		return (Integer) getStateHelper().eval("timeout", 0);
	}

	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }

	public Boolean isClosable() { return (Boolean) getStateHelper().eval("closable", false); }
	public void setClosable(Boolean closable) { getStateHelper().put("closable", closable); }
}