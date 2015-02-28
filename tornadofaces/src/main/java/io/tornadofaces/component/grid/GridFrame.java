package io.tornadofaces.component.grid;

import javax.faces.component.FacesComponent;

@FacesComponent(value = GridFrame.COMPONENT_TYPE, createTag = true, tagName = "grid-frame", namespace = "http://tornadofaces.io/ui")
public class GridFrame extends GridComponent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.GridFrame";

	public String getComponentStyleClass() {
		return "grid-frame";
	}
}
