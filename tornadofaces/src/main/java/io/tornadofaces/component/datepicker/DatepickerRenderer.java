package io.tornadofaces.component.datepicker;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.Map;

@FacesRenderer(rendererType = DatepickerRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class DatepickerRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.DatepickerRenderer";

	public void decode(FacesContext context, UIComponent component) {
		// Use the default input renderer for decoding
		Renderer inputRenderer = context.getRenderKit().getRenderer(HtmlInputText.COMPONENT_FAMILY, "javax.faces.Text");
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String value = params.get(component.getClientId());
		Object convertedValue = inputRenderer.getConvertedValue(context, component, value);
		component.getValueExpression("value").setValue(context.getELContext(), convertedValue);
	}

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		// Render input using the default input renderer
		Renderer inputRenderer = context.getRenderKit().getRenderer(HtmlInputText.COMPONENT_FAMILY, "javax.faces.Text");
		inputRenderer.encodeBegin(context, component);
		inputRenderer.encodeChildren(context, component);
		inputRenderer.encodeEnd(context, component);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Datepicker picker = (Datepicker) component;

		new WidgetBuilder(context, picker)
			.init()
			.nativeAttr("settings", picker.getSettings().toString())
			.finish();
	}
}
