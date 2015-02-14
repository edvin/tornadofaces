package io.tornadofaces.component.list;

import io.tornadofaces.component.common.Color;
import io.tornadofaces.component.common.IconPosition;
import io.tornadofaces.component.common.Orientation;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIColumn;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "foundation.css"),
})
@FacesComponent(value = Ul.COMPONENT_TYPE, createTag = true, tagName = "ul", namespace = "http://tornadofaces.io/ui")
public class Ul extends UIColumn {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Ul";

	public Ul() {
		super();
		setRendererType(UlRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Orientation getOrientation() { return (Orientation) this.getStateHelper().eval("orientation", null); }
	public void setOrientation(Orientation orientation) { this.getStateHelper().put("orientation", orientation); }
	public IconPosition getIconPosition() { return (IconPosition) this.getStateHelper().eval("iconPosition", null); }
	public void setIconPosition(IconPosition iconPosition) { this.getStateHelper().put("iconPosition", iconPosition); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public void setColor(Color color) { this.getStateHelper().put("color", color); }
	public Color getColor() { return (Color) this.getStateHelper().eval("color", null); }
	public boolean isMenuBar() { return (boolean) getStateHelper().eval("menuBar", false); }
	public void setMenuBar(boolean menuBar) { getStateHelper().put("menuBar", menuBar); }
}