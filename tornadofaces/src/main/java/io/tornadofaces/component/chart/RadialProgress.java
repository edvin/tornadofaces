package io.tornadofaces.component.chart;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "d3.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "nv.d3.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "nv.d3.min.css"),
	@ResourceDependency(library = "tornadofaces", name = "radial-progress.js"),
	@ResourceDependency(library = "tornadofaces", name = "radial-progress.css")
})
@FacesComponent(value = "io.tornadofaces.component.RadialProgress", createTag = true, tagName = "radial-progress", namespace = "http://tornadofaces.io/ui")
public class RadialProgress extends UIPanel implements Widget {

	public RadialProgress() {
		super();
		setRendererType(LineChartRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar", null); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getLabel() { return (String) getStateHelper().eval("label"); }
	public void setLabel(String label) { getStateHelper().put("label", label); }
	public String getOnClick() { return (String) getStateHelper().eval("onClick"); }
	public void setOnClick(String onClick) { getStateHelper().put("onClick", onClick); }
	public Integer getDiameter() { return (Integer) getStateHelper().eval("diameter", 150); }
	public void setDiameter(Integer diameter) { getStateHelper().put("diameter", diameter); }
	public Integer getMin() { return (Integer) getStateHelper().eval("min", 0); }
	public void setMin(Integer min) { getStateHelper().put("min", min); }
	public Integer getMax() { return (Integer) getStateHelper().eval("max", 100); }
	public void setMax(Integer max) { getStateHelper().put("max", max); }
	public Integer getValue() { return (Integer) getStateHelper().eval("value"); }
	public void setValue(Integer value) { getStateHelper().put("value", value); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }

}
