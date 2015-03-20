package io.tornadofaces.component.list;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlDataTable;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "highlight.js"),
	@ResourceDependency(library = "tornadofaces", name = "list.js")
})
@FacesComponent(value = List.COMPONENT_TYPE, createTag = true, tagName = "list", namespace = "http://tornadofaces.io/ui")
public class List extends HtmlDataTable implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.List";

	public List() {
		super();
		setRendererType(ListRenderer.RENDERER_TYPE);
		setVar("it");
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getTitle() { return (String) getStateHelper().eval("title"); }
	public void setTitle(String title) { getStateHelper().put("title", title); }
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getFilterFn() { return (String) getStateHelper().eval("filterFn"); }
	public void setFilterFn(String filterFn) { getStateHelper().put("filterFn", filterFn); }
	public Boolean getHighlightFilter() { return (Boolean) getStateHelper().eval("highlightFilter", true); }
	public void setHighlightFilter(Boolean highlightFilter) { getStateHelper().put("highlightFilter", highlightFilter); }

    public Boolean getHideEmpty() { return (Boolean) getStateHelper().eval("hideEmpty", false); }
	public void setHideEmpty(Boolean hideEmpty) { getStateHelper().put("hideEmpty", hideEmpty); }

    public Boolean isEmpty(){
        Object value = getValue();
        if(value instanceof List){
            return ((List)value).isEmpty();
        }
        if(value instanceof Object[]){
            return ((Object[])value).length == 0;
        }

        return value != null;
    }
}