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
	@ResourceDependency(library = "tornadofaces", name = "line-chart.js")
})
@FacesComponent(value = "io.tornadofaces.component.LineChart", createTag = true, tagName = "line-chart", namespace = "http://tornadofaces.io/ui")
public class LineChart extends UIPanel implements Widget {
	public LineChart() {
		super();
		setRendererType(LineChartRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar", null); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }

	public String getxLabel() { return (String) getStateHelper().eval("xLabel"); }
	public void setxLabel(String xLabel) { getStateHelper().put("xLabel", xLabel); }
	public String getxTickFormat() { return (String) getStateHelper().eval("xTickFormat", "d3.format(',r')"); }
	public void setxTickFormat(String xTickFormat) { getStateHelper().put("xTickFormat", xTickFormat); }
	public String getyLabel() { return (String) getStateHelper().eval("yLabel"); }
	public void setyLabel(String yLabel) { getStateHelper().put("yLabel", yLabel); }
	public String getyTickFormat() { return (String) getStateHelper().eval("yTickFormat", "d3.format(',r')"); }
	public void setyTickFormat(String yTickFormat) { getStateHelper().put("yTickFormat", yTickFormat); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getStyle() { return (String) getStateHelper().eval("style"); }
	public void setStyle(String style) { getStateHelper().put("style", style); }
	public String getBeforeConfig() { return (String) getStateHelper().eval("beforeConfig"); }
	public void setBeforeConfig(String beforeConfig) { getStateHelper().put("beforeConfig", beforeConfig); }
	
}
