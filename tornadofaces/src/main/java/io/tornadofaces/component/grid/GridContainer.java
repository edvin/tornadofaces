package io.tornadofaces.component.grid;

import javax.faces.component.FacesComponent;

@FacesComponent(value = GridContainer.COMPONENT_TYPE, createTag = true, tagName = "grid-container", namespace = "http://tornadofaces.io/ui")
public class GridContainer extends GridComponent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.GridContainer";
	public String getComponentStyleClass() {
		return "grid-container";
	}
}
