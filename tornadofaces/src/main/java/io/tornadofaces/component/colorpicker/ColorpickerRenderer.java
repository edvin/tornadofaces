package io.tornadofaces.component.colorpicker;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = ColorpickerRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ColorpickerRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ColorpickerRenderer";

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
		Colorpicker picker = (Colorpicker) component;

		new WidgetBuilder(context, picker)
			.init()
			.attr("showTextField", picker.getShowTextField())
			.nativeAttr("settings", picker.getSettings().toString())
			.nativeAttr("onChange", picker.getOnchange())
			.finish();
	}

}