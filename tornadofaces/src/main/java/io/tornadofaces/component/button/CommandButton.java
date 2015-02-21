package io.tornadofaces.component.button;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

@ResourceDependencies({
	@ResourceDependency(library = "javax.faces", name = "jsf.js"),
	@ResourceDependency(library = "tornadofaces", name = "jquery.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.js"),
	@ResourceDependency(library = "tornadofaces", name = "foundation.css"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.css"),
	@ResourceDependency(library = "tornadofaces", name = "button.js")
})
@FacesComponent(value = "io.tornadofaces.component.CommandButton", createTag = true, tagName = "commandButton", namespace = "http://tornadofaces.io/ui")
public class CommandButton extends ButtonBase {
	public Boolean isInput() { return (Boolean) this.getStateHelper().eval("input", false); }
	public void setInput(Boolean input) { this.getStateHelper().put("input", input); }

	public String getTagName() {
		return isInput() ? "input" : "button";
	}
}