package io.tornadofaces.component.button;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

@ResourceDependency(library = "tornadofaces", name = "button.js")
@FacesComponent(value = "io.tornadofaces.component.CommandLink", createTag = true, tagName = "commandLink", namespace = "http://tornadofaces.io/ui")
public class CommandLink extends ButtonBase {
	public Boolean isButton() { return (Boolean) this.getStateHelper().eval("button", false); }
	public void setButton(Boolean button) { this.getStateHelper().put("button", button); }

	public String getTagName() {
		return "a";
	}
}