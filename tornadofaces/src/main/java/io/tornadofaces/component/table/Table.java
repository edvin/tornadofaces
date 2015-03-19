package io.tornadofaces.component.table;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.list.ListRenderer;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlDataTable;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "hightlight.js"),
	@ResourceDependency(library = "tornadofaces", name = "table.js")
})
@FacesComponent(value = Table.COMPONENT_TYPE, createTag = true, tagName = "table", namespace = "http://tornadofaces.io/ui")
public class Table extends HtmlDataTable implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Table";

	public Table() {
		super();
		setRendererType(ListRenderer.RENDERER_TYPE);
		setVar("it");
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public Boolean getReflow() { return (Boolean) getStateHelper().eval("reflow", true); }
	public void setReflow(Boolean reflow) { getStateHelper().put("reflow", reflow); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getFilterFn() { return (String) getStateHelper().eval("filterFn"); }
	public void setFilterFn(String filterFn) { getStateHelper().put("filterFn", filterFn); }
	public Boolean getHighlightFilter() { return (Boolean) getStateHelper().eval("highlightFilter", true); }
	public void setHighlightFilter(Boolean highlightFilter) { getStateHelper().put("highlightFilter", highlightFilter); }
}