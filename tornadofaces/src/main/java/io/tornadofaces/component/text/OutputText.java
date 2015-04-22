package io.tornadofaces.component.text;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlOutputText;

@FacesComponent(value = OutputText.COMPONENT_TYPE, createTag = true, tagName = "outputText", namespace = "http://tornadofaces.io/ui")
public class OutputText extends HtmlOutputText {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.OutputText";

	public OutputText() {
		super();
		setRendererType(OutputTextRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public Boolean getShow() { return (Boolean) getStateHelper().eval("show", true); }
	public void setShow(Boolean show) { getStateHelper().put("show", show); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getTag() { return (String) getStateHelper().eval("tag", "span"); }
	public void setTag(String tag) { getStateHelper().put("tag", tag); }
}
