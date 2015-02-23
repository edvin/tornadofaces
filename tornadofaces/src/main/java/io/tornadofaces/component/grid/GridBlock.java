package io.tornadofaces.component.grid;

import javax.faces.component.FacesComponent;

@FacesComponent(value = GridBlock.COMPONENT_TYPE, createTag = true, tagName = "grid-block", namespace = "http://tornadofaces.io/ui")
public class GridBlock extends GridComponent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.GridBlock";
	public String getComponentStyleClass() {
		return "grid-block";
	}
}
