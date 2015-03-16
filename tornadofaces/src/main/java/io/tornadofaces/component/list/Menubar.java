package io.tornadofaces.component.list;

import javax.faces.component.FacesComponent;

@FacesComponent(value = Menubar.COMPONENT_TYPE, createTag = true, tagName = "menubar", namespace = "http://tornadofaces.io/ui")
public class Menubar extends Ul {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Menubar";

	public Menubar() {
		super();
		setMenuBar(true);
	}
}
