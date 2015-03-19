package io.tornadofaces.component.source;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependency(library = "tornadofaces", name = "prism.js")
@FacesComponent(value = Source.COMPONENT_TYPE, createTag = true, tagName = "source", namespace = "http://tornadofaces.io/ui")
public class Source extends UIPanel {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Source";

	public Source() {
		super();
		setRendererType(SourceRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public void setLanguage(String language) { getStateHelper().put("language", language); }
	public String getLanguage() { return (String) getStateHelper().eval("language", "markup"); }
	public Boolean getLineNumbers() { return (Boolean) getStateHelper().eval("lineNumbers", true); }
	public void setLineNumbers(Boolean lineNumbers) { getStateHelper().put("lineNumbers", lineNumbers); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
}
