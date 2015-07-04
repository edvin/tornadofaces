package io.tornadofaces.component.slider;

import io.tornadofaces.component.util.ComponentUtils;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = SliderHeaderRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class SliderHeaderRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.SliderHeaderRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		SliderHeader header = (SliderHeader) component;

		writer.startElement("div", header);
		writer.writeAttribute("id", header.getClientId(context), null);
		writer.writeAttribute("class", "slider-header", null);

		// min
		writer.startElement("span", header);
		writer.writeAttribute("class", "slider-min", null);
		writer.writeText(header.getMin(), null);
		writer.endElement("span");

		// max
		writer.startElement("span", header);
		writer.writeAttribute("class", "slider-max", null);
		writer.writeText(header.getMax(), null);
		writer.endElement("span");

		// value
		writer.startElement("h4", header);
		writer.writeText(header.getValue(), null);
		writer.endElement("h4");

		writer.endElement("div");
	}
}
