package io.tornadofaces.component.datepicker;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.json.JSONException;
import io.tornadofaces.json.JSONObject;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.html.HtmlInputText;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "jquery.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.js"),
	@ResourceDependency(library = "tornadofaces", name = "foundation.css"),
	@ResourceDependency(library = "tornadofaces", name = "tornadofaces.css"),
	@ResourceDependency(library = "tornadofaces", name = "pickadate/picker.js"),
	@ResourceDependency(library = "tornadofaces", name = "pickadate/picker.date.js"),
	@ResourceDependency(library = "tornadofaces", name = "pickadate/default.css"),
	@ResourceDependency(library = "tornadofaces", name = "pickadate/default.date.css"),
	@ResourceDependency(library = "tornadofaces", name = "datepicker.js")
})
@FacesComponent(value = Datepicker.COMPONENT_TYPE, createTag = true, tagName = "datepicker", namespace = "http://tornadofaces.io/ui")
public class Datepicker extends HtmlInputText implements Widget {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Datepicker";

	public Datepicker() {
		super();
		setRendererType(DatepickerRenderer.RENDERER_TYPE);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getFormat() { return (String) getStateHelper().eval("format"); }
	public void setFormat(String format) { getStateHelper().put("format", format); }
	public Boolean getEditable() { return (Boolean) getStateHelper().eval("editable"); }
	public void setEditable(Boolean editable) { getStateHelper().put("editable", editable); }
	public String getFormatSubmit() { return (String) getStateHelper().eval("formatSubmit"); }
	public void setFormatSubmit(String formatSubmit) { getStateHelper().put("formatSubmit", formatSubmit); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar", null); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }

	public JSONObject getSettings() {
		JSONObject settings = new JSONObject();
		try {
			Boolean editable = getEditable();
			if (editable != null)
				settings.put("editable", editable);
			settings.put("format", getFormat());
			settings.put("formatSubmit", getFormatSubmit());
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return settings;
	}
}