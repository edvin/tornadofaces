package io.tornadofaces.component.progress;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = ProgressRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ProgressRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ProgressRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		Progress progress = (Progress) component;
		writer.startElement("progress", component);
		if (progress.getId() != null)
			writer.writeAttribute("id", progress.getClientId(), null);
		StyleClass.of(progress.getStyleClass()).add(progress.getPriority()).write(writer);
		writer.writeAttribute("value", progress.getValue(), null);
		writer.writeAttribute("max", progress.getMax(), null);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		writer.endElement("progress");
	}
}
