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
	@ResourceDependency(library = "tornadofaces", name = "moment.min.js"),
	@ResourceDependency(library = "tornadofaces", name = "pickaday.js"),
	@ResourceDependency(library = "tornadofaces", name = "pickaday.css"),
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
	public String getPosition() { return (String) getStateHelper().eval("position"); }
	public void setPosition(String position) { getStateHelper().put("position", position); }
	public String getContainer() { return (String) getStateHelper().eval("container"); }
	public void setContainer(String container) { getStateHelper().put("container", container); }
	public String getTrigger() { return (String) getStateHelper().eval("trigger"); }
	public void setTrigger(String trigger) { getStateHelper().put("trigger", trigger); }
	public String getOnSelect() { return (String) getStateHelper().eval("onSelect"); }
	public void setOnSelect(String onSelect) { getStateHelper().put("onSelect", onSelect); }
	public Boolean getReposition() { return (Boolean) getStateHelper().eval("reposition"); }
	public void setReposition(Boolean reposition) { getStateHelper().put("reposition", reposition); }
	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar", null); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }

	public JSONObject getSettings() {
		JSONObject settings = new JSONObject();
		try {
			settings.put("format", getFormat());

			String position = getPosition();
			if (position != null)
				settings.put("position", position);

			String container = getContainer();
			if (container != null)
				settings.put("container", container);

			String onSelect = getOnselect();
			if (onSelect != null)
				settings.put("onSelect", onSelect);

			Boolean reposition = getReposition();
			if (reposition != null)
				settings.put("repoisition", reposition);

			String trigger = getTrigger();
			if (trigger != null)
				settings.put("trigger", trigger);
			
		} catch (JSONException e) {
			throw new RuntimeException(e);
		}
		return settings;
	}
}