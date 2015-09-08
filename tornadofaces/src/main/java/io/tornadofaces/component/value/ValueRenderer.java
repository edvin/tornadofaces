package io.tornadofaces.component.value;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputHidden;
import javax.faces.context.FacesContext;
import javax.faces.convert.ConverterException;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;

@FacesRenderer(rendererType = ValueRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ValueRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ValueRenderer";

	public void decode(FacesContext context, UIComponent component) {
		getDefaultRenderer(context).decode(context, component);
	}

	private Renderer getDefaultRenderer(FacesContext context) {
		return context.getRenderKit().getRenderer(HtmlInputHidden.COMPONENT_FAMILY, "javax.faces.Hidden");
	}

	public Object getConvertedValue(FacesContext context, UIComponent component, Object submittedValue) throws ConverterException {
		return getDefaultRenderer(context).getConvertedValue(context, component, submittedValue);
	}

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		Renderer inputRenderer = getDefaultRenderer(context);
		inputRenderer.encodeBegin(context, component);
		inputRenderer.encodeChildren(context, component);
		inputRenderer.encodeEnd(context, component);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Value value = (Value) component;

		WidgetBuilder builder = new WidgetBuilder(context, value)
			.init()
			.attr("name", value.getName());

		JSONArray valueChangeBehaviors = encodeAjaxBehaviors(context, "valueChange", value);

		if (valueChangeBehaviors != null) {
			JSONObject behaviors = new JSONObject();
			behaviors.put("valueChange", valueChangeBehaviors);
			builder.nativeAttr("behaviors", behaviors.toString());
		}

		builder.finish();
	}
}