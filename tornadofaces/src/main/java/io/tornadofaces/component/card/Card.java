package io.tornadofaces.component.card;

import io.tornadofaces.component.common.Color;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@FacesComponent(value = Card.COMPONENT_TYPE, createTag = true, tagName = "card", namespace = "http://tornadofaces.io/ui")
public class Card extends UIPanel {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Card";

	public Card() {
		super();
		setRendererType(CardRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public void setColor(Color color) { this.getStateHelper().put("color", color); }
	public Color getColor() { return (Color) this.getStateHelper().eval("color", null); }
	public String getTitle() { return (String) getStateHelper().eval("title"); }
	public void setTitle(String title) { getStateHelper().put("title", title); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
}