package io.tornadofaces.component.input;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.flippanel.FlipPanel;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.Map;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;

@FacesRenderer(rendererType = SwitchRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SwitchRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SwitchRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("div", component);
		writer.writeAttribute("id", component.getClientId(), null);
		Switch switchComponent = (Switch) component;
		StyleClass.of(switchComponent.getSize()).add("switch").write(writer);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.startElement("label", component);

		Switch switchComponent = (Switch) component;

		writer.writeAttribute("for", switchComponent.getCheckbox().getClientId(context), null);
		writer.endElement("label");
		writer.endElement("div");


		WidgetBuilder builder = new WidgetBuilder(context, switchComponent)
			.init()
			.nativeAttr("onChange", switchComponent.getOnChange());

		JSONArray flipBehaviors = encodeAjaxBehaviors(context, "change", switchComponent);
		if (flipBehaviors != null) {
			JSONObject behaviors = new JSONObject();
			behaviors.put("change", flipBehaviors);
			builder.nativeAttr("behaviors", behaviors.toString());
		}

		builder.finish();
	}

	public void decode(FacesContext context, UIComponent component) {
		Switch _switch = (Switch) component;
		_switch.getCheckbox().decode(context);
		decodeBehaviors(context, component);
	}

}