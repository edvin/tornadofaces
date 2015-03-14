package io.tornadofaces.component.flippanel;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.button.ButtonBase;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.event.FlipPanelEvent;
import io.tornadofaces.json.JSONArray;
import io.tornadofaces.json.JSONObject;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.component.behavior.AjaxBehavior;
import javax.faces.component.behavior.ClientBehavior;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import static io.tornadofaces.component.util.ComponentUtils.encodeAjaxBehaviors;
import static io.tornadofaces.component.util.ComponentUtils.resolveIds;

@FacesRenderer(rendererType = io.tornadofaces.component.flippanel.FlipPanelRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class FlipPanelRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.FlipPanelRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		FlipPanel panel = (FlipPanel) component;
		writer.startElement("div", panel);
		writer.writeAttribute("id", panel.getClientId(), null);

		StyleClass.of("flip-panel")
			.add(panel.getStyleClass())
			.add("flipped", panel.isFlipped())
			.write(writer);

		String style = panel.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		UIComponent header = panel.getFacet("header");
		if (header != null)
			header.encodeAll(context);

		writer.startElement("div", panel);
		writer.writeAttribute("class", "flip-content", null);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		FlipPanel panel = (FlipPanel) component;

		UIComponent front = panel.getFacet("front");
		if (front != null) {
			writer.startElement("div", panel);
			StyleClass.of("front").add("box", panel.isBox()).write(writer);
			front.encodeAll(context);
			writer.endElement("div");
		}

		UIComponent back = panel.getFacet("back");
		if (back != null) {
			writer.startElement("div", panel);
			StyleClass.of("back").add("box", panel.isBox()).write(writer);
			back.encodeAll(context);
			writer.endElement("div");
		}
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("div");
		writer.endElement("div");

		FlipPanel panel = (FlipPanel) component;

		WidgetBuilder builder = new WidgetBuilder(context, panel)
			.init()
			.attr("duration", panel.getDuration())
			.attr("flipped", panel.isFlipped())
			.attr("mode", panel.getMode())
			.nativeAttr("onFlip", panel.getOnFlip());

		JSONArray flipBehaviors = encodeAjaxBehaviors(context, "flip", panel);
		if (flipBehaviors != null) {
			JSONObject behaviors = new JSONObject();
			behaviors.put("flip", flipBehaviors);
			builder.nativeAttr("behaviors", behaviors.toString());
		}

		builder.finish();
	}

	public void decode(FacesContext context, UIComponent component) {
		String clientId = component.getClientId();
		Map<String, String> params = context.getExternalContext().getRequestParameterMap();
		String source = params.get("javax.faces.source");

		if (clientId.equals(source)) {
			FlipPanel panel = (FlipPanel) component;
			String flipped = params.get(clientId + "_flipped");
			if (flipped != null)
				panel.setFlipped(Boolean.valueOf(flipped));
		}

		super.decode(context, component);
	}
}