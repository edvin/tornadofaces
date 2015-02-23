package io.tornadofaces.component.grid;

import javax.faces.component.FacesComponent;

@FacesComponent(value = GridContent.COMPONENT_TYPE, createTag = true, tagName = "grid-content", namespace = "http://tornadofaces.io/ui")
public class GridContent extends GridComponent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.GridContent";

	public String getComponentStyleClass() {
		return "grid-content";
	}
}
