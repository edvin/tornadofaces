package io.tornadofaces.component.text;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;

import java.io.IOException;

import static io.tornadofaces.component.util.ComponentUtils.COMPONENT_FAMILY;

@FacesRenderer(rendererType = OutputTextRenderer.RENDERER_TYPE, componentFamily = COMPONENT_FAMILY)
public class OutputTextRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.OutputTextRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		OutputText text = (OutputText) component;
		writer.startElement(text.getTag(), text);
		writer.writeAttribute("id", text.getClientId(context), null);

		if (text.getShow()) {
			StyleClass.of(text.getStyleClass()).write(writer);
			String style = text.getStyle();
			if (style != null)
				writer.writeAttribute("style", style, null);

			Object txt = text.getValue();
			if (txt != null)
				writer.writeText(txt, null);
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		OutputText text = (OutputText) component;
		writer.endElement(text.getTag());
	}
}