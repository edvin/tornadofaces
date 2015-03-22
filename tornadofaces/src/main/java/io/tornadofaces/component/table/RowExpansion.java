package io.tornadofaces.component.table;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UIComponentBase;

@FacesComponent(value = RowExpansion.COMPONENT_TYPE, createTag = true, tagName = "rowExpansion", namespace = "http://tornadofaces.io/ui")
public class RowExpansion extends UIComponentBase {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.RowExpansion";

	public RowExpansion() {
		super();
		setRendererType(RowExpansionRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
}
