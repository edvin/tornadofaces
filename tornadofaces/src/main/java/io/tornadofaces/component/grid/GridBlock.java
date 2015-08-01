package io.tornadofaces.component.grid;

import io.tornadofaces.component.common.Orientation;

import javax.faces.component.FacesComponent;

@FacesComponent(value = GridBlock.COMPONENT_TYPE, createTag = true, tagName = "grid-block", namespace = "http://tornadofaces.io/ui")
public class GridBlock extends GridComponent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.GridBlock";
	public String getComponentStyleClass() {
		return "grid-block";
	}

	public Orientation getOrientation() { return (Orientation) this.getStateHelper().eval("orientation", null); }
	public void setOrientation(Orientation orientation) { this.getStateHelper().put("orientation", orientation); }
	public Boolean getGutters() { return (Boolean) getStateHelper().eval("gutters"); }
	public void setGutters(Boolean gutters) { getStateHelper().put("gutters", gutters); }

}
