package io.tornadofaces.component.modal;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.common.Color;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependency(library = "tornadofaces", name = "modal.js")
@FacesComponent(value = Modal.COMPONENT_TYPE, createTag = true, tagName = "modal", namespace = "http://tornadofaces.io/ui")
public class Modal extends UIPanel implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Modal";

	public Modal() {
		super();
		setRendererType(ModalRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getTitle() { return (String) getStateHelper().eval("title"); }
	public void setTitle(String title) { getStateHelper().put("title", title); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public Boolean getOpen() { return (Boolean) getStateHelper().eval("open", false); }
	public void setOpen(Boolean open) { getStateHelper().put("open", open); }
	public void setColor(Color color) { this.getStateHelper().put("color", color); }
	public Color getColor() { return (Color) this.getStateHelper().eval("color", Color.primary); }
}
