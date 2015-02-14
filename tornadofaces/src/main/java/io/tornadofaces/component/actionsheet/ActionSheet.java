package io.tornadofaces.component.actionsheet;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.UIPanel;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "jquery.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.js"),
	@ResourceDependency(library = "tornadofaces", name = "foundation.css"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.css"),
	@ResourceDependency(library = "tornadofaces", name = "actionsheet.js")
})
@FacesComponent(value = ActionSheet.COMPONENT_TYPE, createTag = true, tagName = "actionsheet", namespace = "http://tornadofaces.io/ui")
public class ActionSheet extends UIPanel implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.ActionSheet";

	public ActionSheet() {
		super();
		setRendererType(ActionSheetRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}
	public String getStyleClass() { return (String) getStateHelper().eval("styleClass"); }
	public void setStyleClass(String styleClass) { getStateHelper().put("styleClass", styleClass); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar"); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getTitle() { return (String) getStateHelper().eval("title"); }
	public void setTitle(String title) { getStateHelper().put("title", title); }
	public String getDescription() { return (String) getStateHelper().eval("description"); }
	public void setDescription(String description) { getStateHelper().put("description", description); }
	public void setPosition(String position) { getStateHelper().put("position", position); }
	public String getPosition() { return (String) getStateHelper().eval("position", "bottom"); }
}
