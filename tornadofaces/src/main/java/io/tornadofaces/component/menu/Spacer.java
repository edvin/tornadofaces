package io.tornadofaces.component.menu;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.UICommand;

@FacesComponent(value = ContextMenu.COMPONENT_TYPE, createTag = true, tagName = "spacer", namespace = "http://tornadofaces.io/ui")
public class Spacer extends UICommand {

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}
}

