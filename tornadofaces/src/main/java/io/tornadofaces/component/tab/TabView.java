package io.tornadofaces.component.tab;

import io.tornadofaces.component.common.Orientation;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;

@ResourceDependency(library = "tornadofaces", name = "tabview.js")
@FacesComponent(value = TabView.COMPONENT_TYPE, createTag = true, tagName = "tabView", namespace = "http://tornadofaces.io/ui")
public class TabView extends TabParent {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.TabView";

	private enum PropertyKeys { orientation, contentsStyleClass }

	public TabView() {
		super();
		setRendererType(TabViewRenderer.RENDERER_TYPE);
	}

	public String getTabRendererType() {
		return TabViewTabRenderer.RENDERER_TYPE;
	}

	public Orientation getOrientation() { return (Orientation) this.getStateHelper().eval(PropertyKeys.orientation, Orientation.horizontal); }
	public void setOrientation(Orientation orientation) { this.getStateHelper().put(PropertyKeys.orientation, orientation); }
	public String getContentsStyleClass() { return (String) getStateHelper().eval(PropertyKeys.contentsStyleClass); }
	public void setContentsStyleClass(String contentsStyleClass) { getStateHelper().put(PropertyKeys.contentsStyleClass, contentsStyleClass); }
}
