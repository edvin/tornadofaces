package io.tornadofaces.component.reveal;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "tweenmax.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "reveal.js")
})
@FacesComponent(value = "io.tornadofaces.component.Reveal", createTag = true, tagName = "reveal", namespace = "http://tornadofaces.io/ui")
public class Reveal extends UIPanel implements Widget {
	public enum Effect { fade }
	public enum Mode { visibility, display }
	
	public Reveal() {
		super();
		setRendererType(RevealRenderer.RENDERER_TYPE);
	}
	
	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public void setEffect(Effect effect) { getStateHelper().put("effect", effect); }
	public Effect getEffect() { return (Effect) getStateHelper().eval("effect", Effect.fade); }
	public void setMode(Mode mode) { getStateHelper().put("mode", mode); }
	public Mode getMode() { return (Mode) getStateHelper().eval("mode", Mode.display); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public Boolean getOnload() { return (Boolean) getStateHelper().eval("onload"); }
	public void setOnload(Boolean onload) { getStateHelper().put("onload", onload); }
	public Integer getDuration() { return (Integer) getStateHelper().eval("duration", 500); }
	public void setDuration(Integer duration) { getStateHelper().put("duration", duration); }
}
