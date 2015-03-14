package io.tornadofaces.component.colorpicker;

import io.tornadofaces.component.api.Widget;
import io.tornadofaces.component.datepicker.DatepickerRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.json.JSONObject;

import javax.faces.application.ResourceDependencies;
import javax.faces.application.ResourceDependency;
import javax.faces.component.FacesComponent;
import javax.faces.component.behavior.ClientBehaviorHolder;
import javax.faces.component.html.HtmlInputText;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

@ResourceDependencies({
	@ResourceDependency(library = "tornadofaces", name = "spectrum.js"),
	@ResourceDependency(library = "tornadofaces", name = "colorpicker.js")
})
@FacesComponent(value = Colorpicker.COMPONENT_TYPE, createTag = true, tagName = "colorpicker", namespace = "http://tornadofaces.io/ui")
public class Colorpicker extends HtmlInputText implements Widget, ClientBehaviorHolder {
	public static final String COMPONENT_TYPE = "io.tornadofaces.component.Colorpicker";

	public Colorpicker() {
		super();
		setRendererType(ColorpickerRenderer.RENDERER_TYPE);
		// If we are placed in a form element, make sure we render an inline wrapper
		// so the textfield and color picker dropdown can render side-by-side
		getAttributes().put("renderInlineLabelWrapper", true);
	}

	public String getFamily() {
		return ComponentUtils.COMPONENT_FAMILY;
	}

	public String getDefaultEventName() {
		return "change";
	}

	public Collection<String> getEventNames() {
		return Collections.singletonList(getDefaultEventName());
	}

	public String getWidgetVar() { return (String) getStateHelper().eval("widgetVar", null); }
	public void setWidgetVar(String widgetVar) { getStateHelper().put("widgetVar", widgetVar); }
	public String getOnChange() { return (String) getStateHelper().eval("onChange", null); }
	public void setOnChange(String onChange) { getStateHelper().put("onChange", onChange); }

	public Integer getMaxSelectionSize() { return (Integer) getStateHelper().eval("maxSelectionSize", null); }
	public void setMaxSelectionSize(Integer maxSelectionSize) { getStateHelper().put("maxSelectionSize", maxSelectionSize); }
	public List<List<String>> getPalette() { return (List<List<String>>) getStateHelper().eval("palette", null); }
	public void setPalette(List<List<String>> palette) { getStateHelper().put("palette", palette); }
	public List<String> getSelectionPalette() { return (List<String>) getStateHelper().eval("selectionPalette", null); }
	public void setSelectionPalette(List<String> selectionPalette) { getStateHelper().put("selectionPalette", selectionPalette); }
	public String getCancelText() { return (String) getStateHelper().eval("cancelText", null); }
	public void setCancelText(String cancelText) { getStateHelper().put("cancelText", cancelText); }
	public String getPreferredFormat() { return (String) getStateHelper().eval("preferredFormat", "hex"); }
	public void setPreferredFormat(String preferredFormat) { getStateHelper().put("preferredFormat", preferredFormat); }
	public String getContainerClassName() { return (String) getStateHelper().eval("containerClassName", null); }
	public void setContainerClassName(String containerClassName) { getStateHelper().put("containerClassName", containerClassName); }
	public String getReplacerClassName() { return (String) getStateHelper().eval("replacerClassName", null); }
	public void setReplacerClassName(String replacerClassName) { getStateHelper().put("replacerClassName", replacerClassName); }
	public String getChooseText() { return (String) getStateHelper().eval("chooseText", null); }
	public void setChooseText(String chooseText) { getStateHelper().put("chooseText", chooseText); }
	public String getLocalStorageKey() { return (String) getStateHelper().eval("localStorageKey", null); }
	public void setLocalStorageKey(String localStorageKey) { getStateHelper().put("localStorageKey", localStorageKey); }
	public String getTogglePaletteMoreText() { return (String) getStateHelper().eval("togglePaletteMoreText", null); }
	public void setTogglePaletteMoreText(String togglePaletteMoreText) { getStateHelper().put("togglePaletteMoreText", togglePaletteMoreText); }
	public String getTogglePaletteLessText() { return (String) getStateHelper().eval("togglePaletteLessText", null); }
	public void setTogglePaletteLessText(String togglePaletteLessText) { getStateHelper().put("togglePaletteLessText", togglePaletteLessText); }

	public Boolean getFlat() { return (Boolean) getStateHelper().eval("flat", null); }
	public void setFlat(Boolean flat) { getStateHelper().put("flat", flat); }
	public Boolean getShowButtons() { return (Boolean) getStateHelper().eval("showButtons", null); }
	public void setShowButtons(Boolean showButtons) { getStateHelper().put("showButtons", showButtons); }
	public Boolean getClickoutFiresChange() { return (Boolean) getStateHelper().eval("clickoutFiresChange", true); }
	public void setClickoutFiresChange(Boolean clickoutFiresChange) { getStateHelper().put("clickoutFiresChange", clickoutFiresChange); }
	public Boolean getShowSelectionPalette() { return (Boolean) getStateHelper().eval("showSelectionPalette", null); }
	public void setShowSelectionPalette(Boolean showSelectionPalette) { getStateHelper().put("showSelectionPalette", showSelectionPalette); }
	public Boolean getShowPalette() { return (Boolean) getStateHelper().eval("showPalette", null); }
	public void setShowPalette(Boolean showPalette) { getStateHelper().put("showPalette", showPalette); }
	public Boolean getShowPaletteOnly() { return (Boolean) getStateHelper().eval("showPaletteOnly", null); }
	public void setShowPaletteOnly(Boolean showPaletteOnly) { getStateHelper().put("showPaletteOnly", showPaletteOnly); }
	public Boolean getHideAfterPaletteSelect() { return (Boolean) getStateHelper().eval("hideAfterPaletteSelect", null); }
	public void setHideAfterPaletteSelect(Boolean hideAfterPaletteSelect) { getStateHelper().put("hideAfterPaletteSelect", hideAfterPaletteSelect); }
	public Boolean getTogglePaletteOnly() { return (Boolean) getStateHelper().eval("togglePaletteOnly", null); }
	public void setTogglePaletteOnly(Boolean togglePaletteOnly) { getStateHelper().put("togglePaletteOnly", togglePaletteOnly); }
	public Boolean getShowAlpha() { return (Boolean) getStateHelper().eval("showAlpha", null); }
	public void setShowAlpha(Boolean showAlpha) { getStateHelper().put("showAlpha", showAlpha); }
	public Boolean getAllowEmpty() { return (Boolean) getStateHelper().eval("allowEmpty", null); }
	public void setAllowEmpty(Boolean allowEmpty) { getStateHelper().put("allowEmpty", allowEmpty); }
	public Boolean getShowInitial() { return (Boolean) getStateHelper().eval("showInitial", null); }
	public void setShowInitial(Boolean showInitial) { getStateHelper().put("showInitial", showInitial); }
	public Boolean getShowInput() { return (Boolean) getStateHelper().eval("showInput", true); }
	public void setShowInput(Boolean showInput) { getStateHelper().put("showInput", showInput); }
	public Boolean getShowTextField() { return (Boolean) getStateHelper().eval("showTextField", true); }
	public void setShowTextField(Boolean showTextField) { getStateHelper().put("showTextField", showTextField); }

	public JSONObject getSettings() {
		JSONObject settings = new JSONObject();
		settings.put("disabled", isDisabled() ? true : null);
		settings.put("maxSelectionSize", getMaxSelectionSize());
		settings.put("selectionPalette", getSelectionPalette());
		settings.put("palette", getPalette());
		settings.put("cancelText", getCancelText());
		settings.put("flat", getFlat());
		settings.put("chooseText", getChooseText());
		settings.put("localStorageKey", getLocalStorageKey());
		settings.put("togglePaletteMoreText", getTogglePaletteMoreText());
		settings.put("togglePaletteLessText", getTogglePaletteLessText());
		settings.put("containerClassName", getContainerClassName());
		settings.put("replacerClassName", getReplacerClassName());
		settings.put("preferredFormat", getPreferredFormat());
		settings.put("flat", getFlat());
		settings.put("hideAfterPaletteSelect", getHideAfterPaletteSelect());
		settings.put("showInput", getShowInput());
		settings.put("showButtons", getShowButtons());
		settings.put("showInitial", getShowInitial());
		settings.put("allowEmpty", getAllowEmpty());
		settings.put("showAlpha", getShowAlpha());
		settings.put("showPalette", getShowPalette());
		settings.put("showPaletteOnly", getShowPaletteOnly());
		settings.put("togglePaletteOnly", getTogglePaletteOnly());
		settings.put("showSelectionPalette", getShowSelectionPalette());
		settings.put("clickoutFiresChange", getClickoutFiresChange());
		return settings;
	}

}