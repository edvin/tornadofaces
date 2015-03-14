package io.tornadofaces.component.accordion;

import io.tornadofaces.component.tab.TabParent;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

@ResourceDependency(library = "tornadofaces", name = "accordion.js")
@FacesComponent(value = Accordion.COMPONENT_TYPE, createTag = true, tagName = "accordion", namespace = "http://tornadofaces.io/ui")
public class Accordion extends TabParent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Accordion";

	public enum PropertyKeys {multi, collapsible}

	public Accordion() {
		super();
		setRendererType(AccordionRenderer.RENDERER_TYPE);
	}

	public String getTabRendererType() {
		return AccordionTabRenderer.RENDERER_TYPE;
	}

	public Boolean isMulti() {
		return (Boolean) getStateHelper().eval(PropertyKeys.multi, false);
	}

	public void setMulti(Boolean multi) {
		getStateHelper().put(PropertyKeys.multi, multi);
	}

	public Boolean isCollapsible() {
		return (Boolean) getStateHelper().eval(PropertyKeys.collapsible, isMulti());
	}
	public void setCollapsible(Boolean collapsible) {
		getStateHelper().put(PropertyKeys.collapsible, collapsible);
	}
	public Boolean isItemSpacing() {
		return (Boolean) getStateHelper().eval("itemSpacing", true);
	}
	public void setItemSpacing(Boolean itemSpacing) {
		getStateHelper().put("itemSpacing", itemSpacing);
	}
}
