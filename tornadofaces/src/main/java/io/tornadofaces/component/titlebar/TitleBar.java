package io.tornadofaces.component.titlebar;

import io.tornadofaces.component.common.Color;
import io.tornadofaces.component.common.Position;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIColumn;

@FacesComponent(value = TitleBar.COMPONENT_TYPE, createTag = true, tagName = "titleBar", namespace = "http://tornadofaces.io/ui")
public class TitleBar extends UIColumn {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.TitleBar";

	public TitleBar() {
		super();
		setRendererType(TitleBarRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Position getTitlePosition() { return (Position) getStateHelper().eval("titlePosition", Position.center); }
	public void setTitlePosition(Position titlePosition) { getStateHelper().put("titlePosition", titlePosition); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getTitle() { return (String) getStateHelper().eval("title"); }
	public void setTitle(String title) { getStateHelper().put("title", title); }
	public void setColor(Color color) { this.getStateHelper().put("color", color); }
	public Color getColor() { return (Color) this.getStateHelper().eval("color", null); }
}
