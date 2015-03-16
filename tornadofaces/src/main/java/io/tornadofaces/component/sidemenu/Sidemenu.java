package io.tornadofaces.component.sidemenu;

import io.tornadofaces.component.common.Color;
import io.tornadofaces.component.common.IconPosition;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@FacesComponent(value = Sidemenu.COMPONENT_TYPE, createTag = true, tagName = "sidemenu", namespace = "http://tornadofaces.io/ui")
public class Sidemenu extends UIPanel {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Sidemenu";

	public Sidemenu() {
		super();
		setRendererType(SidemenuRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getPosition() { return (String) getStateHelper().eval("position", "left"); }
	public void setPosition(String position) { getStateHelper().put("position", position); }
	public void setColor(Color color) { this.getStateHelper().put("color", color); }
	public Color getColor() { return (Color) this.getStateHelper().eval("color", null); }
	public IconPosition getIconPosition() { return (IconPosition) this.getStateHelper().eval("iconPosition", null); }
	public void setIconPosition(IconPosition iconPosition) { this.getStateHelper().put("iconPosition", iconPosition); }
}