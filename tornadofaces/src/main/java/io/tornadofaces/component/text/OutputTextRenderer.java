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

		if (text.getEmpty()) {
			StyleClass.of(text.getEmptyClass()).write(writer);
		} else {
			StyleClass.of(text.getStyleClass()).write(writer);
			String style = text.getStyle();
			if (style != null)
				writer.writeAttribute("style", style, null);
		}
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		OutputText text = (OutputText) component;
		if (text.getEmpty())
			return;

		for (UIComponent child : component.getChildren())
			child.encodeAll(context);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		OutputText text = (OutputText) component;
		writer.endElement(text.getTag());
	}
}
