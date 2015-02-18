package io.tornadofaces.component.slider;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@FacesRenderer(rendererType = SliderRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SliderRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SliderRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Slider slider = (Slider) component;
		writer.startElement("div", component);
		writer.writeAttribute("id", slider.getClientId(), null);
		StyleClass.of("slider").add(slider.getStyleClass()).write(writer);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		Slider slider = (Slider) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement("div");

		WidgetBuilder builder = new WidgetBuilder(context, slider)
			.init()
			.attr("lowerId", slider.getChildren().get(0).getClientId())
			.attr("upperId", slider.getChildren().get(1).getClientId())
			.nativeAttr("settings", slider.getSettings().toString())
			.nativeAttr("onSlide", slider.getOnSlide());

		List<ClientBehavior> behaviors = slider.getClientBehaviors().get("change");
		if (behaviors != null && !behaviors.isEmpty()) {
			JSONObject behaviors_o = new JSONObject();
			JSONArray array = new JSONArray();
			for (ClientBehavior behavior : behaviors) {
				if (behavior instanceof AjaxBehavior) {
					AjaxBehavior ab = (AjaxBehavior) behavior;
					JSONObject o = new JSONObject();
					if (ab.getExecute() != null)
						o.put("execute", String.join(" ", ab.getExecute()));

					if (ab.getRender() != null)
						o.put("render", String.join(" ", ab.getRender()));
					array.put(o);
				}
			}
			behaviors_o.put("change", array);
			builder.nativeAttr("behaviors", behaviors_o.toString());
		}

		builder.finish();
	}

}
