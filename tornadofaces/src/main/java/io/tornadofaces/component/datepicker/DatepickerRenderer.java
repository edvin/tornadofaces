package io.tornadofaces.component.datepicker;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;

@FacesRenderer(rendererType = DatepickerRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class DatepickerRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.DatepickerRenderer";

	public void decode(FacesContext context, UIComponent component) {
		getDefaultInputRenderer(context).decode(context, component);
	}

	private Renderer getDefaultInputRenderer(FacesContext context) {
		return context.getRenderKit().getRenderer(HtmlInputText.COMPONENT_FAMILY, "javax.faces.Text");
	}

	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
		return getDefaultInputRenderer(context).getConvertedValue(context, component, submittedValue);
	}

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		Renderer inputRenderer = getDefaultInputRenderer(context);
		inputRenderer.encodeBegin(context, component);
		inputRenderer.encodeChildren(context, component);
		inputRenderer.encodeEnd(context, component);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Datepicker picker = (Datepicker) component;

		new WidgetBuilder(context, picker)
			.init()
			.attr("locale", picker.getLocale())
			.nativeAttr("settings", picker.getSettings().toString())
			.finish();

		// change ajax event is available because it attaches to the textfield and uses it's default
		// change event handling. The datepicker triggers this by updating the input in the browser,
		// so we don't need to handle this ourselves.
	}
}
