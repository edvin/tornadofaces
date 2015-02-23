package io.tornadofaces.component.grid;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.UIPanel;

public abstract class GridComponent extends UIPanel {
	public abstract String getComponentStyleClass();
	
	public GridComponent() {
		super();
		setRendererType(GridComponentRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public Integer getSmall() { return (Integer) getStateHelper().eval("small", null); }
	public void setSmall(Integer small) { getStateHelper().put("small", small); }
	public Integer getMedium() { return (Integer) getStateHelper().eval("medium", null); }
	public void setMedium(Integer medium) { getStateHelper().put("medium", medium); }
	public Integer getLarge() { return (Integer) getStateHelper().eval("large", null); }
	public void setLarge(Integer large) { getStateHelper().put("large", large); }
}
