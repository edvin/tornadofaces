package io.tornadofaces.component.flippanel;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = io.tornadofaces.component.flippanel.FlipPanelRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class FlipPanelRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.FlipPanelRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		FlipPanel panel = (FlipPanel) component;
		writer.startElement("div", panel);
		writer.writeAttribute("id", panel.getClientId(), null);
		
		StyleClass.of("flip-panel")
			.add(panel.getStyleClass())
			.add("box", panel.isBox())
			.add("flipped", panel.isFlipped())
			.write(writer);

		String style = panel.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		writer.startElement("div", panel);
		writer.writeAttribute("class", "flip-wrapper", null);
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		FlipPanel panel = (FlipPanel) component;
		
		UIComponent front = panel.getFacet("front");
		if (front != null) {
			writer.startElement("div", panel);
			writer.writeAttribute("class", "front", null);
			front.encodeAll(context);
			writer.endElement("div");
		}

		UIComponent back = panel.getFacet("back");
		if (back != null) {
			writer.startElement("div", panel);
			writer.writeAttribute("class", "back", null);
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

		new WidgetBuilder(context, panel)
			.init()
			.nativeAttr("onFlip", panel.getOnFlip())
			.finish();
	}
}