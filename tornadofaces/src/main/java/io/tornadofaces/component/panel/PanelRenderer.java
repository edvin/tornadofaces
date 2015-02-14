package io.tornadofaces.component.panel;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = PanelRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class PanelRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.PanelRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Panel panel = (Panel) component;
		writer.startElement("div", panel);
		writer.writeAttribute("id", panel.getClientId(), null);

		StyleClass.of("panel").add("panel-" + panel.getPosition()).add(panel.getStyleClass()).write(writer);

		String style = panel.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		if (panel.getClosable()) {
			writer.startElement("a", panel);
			writer.writeAttribute("class", "close-button", null);
			writer.write("x");
			writer.endElement("a");
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Panel panel = (Panel) component;
		writer.endElement("div");

		new WidgetBuilder(context, panel)
			.init()
			.attr("open", panel.getOpen())
			.attr("position", panel.getPosition().toString())
			.finish();
	}
}
