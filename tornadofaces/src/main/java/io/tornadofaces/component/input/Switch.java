package io.tornadofaces.component.input;

import io.tornadofaces.component.common.Size;
import io.tornadofaces.component.util.ComponentUtils;

import javax.el.ValueExpression;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;
import javax.faces.component.html.HtmlSelectBooleanCheckbox;

@FacesComponent(value = Switch.COMPONENT_TYPE, createTag = true, tagName = "switch", namespace = "http://tornadofaces.io/ui")
public class Switch extends UIPanel {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Switch";

	private HtmlSelectBooleanCheckbox checkbox;

	public Switch() {
		super();
		setRendererType(SwitchRenderer.RENDERER_TYPE);
		checkbox = new HtmlSelectBooleanCheckbox();
		checkbox.setId("checkbox");
		getChildren().add(checkbox);
	}

	public Size getSize() { return (Size) getStateHelper().eval("size", null); }
	public void setSize(Size size) { getStateHelper().put("size", size); }

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public ValueExpression getValueExpression(String name) {
		return checkbox.getValueExpression(name);
	}

	public void setValueExpression(String name, ValueExpression binding) {
		checkbox.setValueExpression(name, binding);
	}

	public HtmlSelectBooleanCheckbox getCheckbox() {
		return checkbox;
	}
}