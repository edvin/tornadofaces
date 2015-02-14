package io.tornadofaces.component.input;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;

@FacesRenderer(rendererType = FormElementRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class FormElementRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.FormElementRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		FormElement elem = (FormElement) component;
		ResponseWriter writer = context.getResponseWriter();

		boolean idWritten = false;
		if ("grid-content".equals(elem.getLayout())) {
			writer.startElement("div", elem);
			writer.writeAttribute("id", elem.getClientId(context), null);
			idWritten = true;

			StyleClass styleClass = StyleClass.of("grid-content");

			Integer small = elem.getSmall();
			if (small != null)
				styleClass.add("small-" + small);
			Integer medium = elem.getMedium();
			if (medium != null)
				styleClass.add("medium-" + medium);
			Integer large = elem.getLarge();
			if (large != null)
				styleClass.add("large-" + large);

			styleClass.write(writer);
		}

		writer.startElement("label", component);
		StyleClass.of(elem.getStyleClass()).write(writer);
		String label = elem.getLabel();
		if (label != null)
			writer.write(label);

		if (!elem.getLabelWrapsInput())
			writer.endElement("label");

		if (elem.shouldRenderInlineLabelSpan()) {
			writer.startElement("span", component);
			if (!idWritten)
				writer.writeAttribute("id", elem.getClientId(context), null);
			writer.writeAttribute("class", "inline-label", null);
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		FormElement elem = (FormElement) component;
		ResponseWriter writer = context.getResponseWriter();

		if (elem.getLabelWrapsInput())
			writer.endElement("label");

		if (elem.shouldRenderInlineLabelSpan())
			writer.endElement("span");

		if ("grid-content".equals(elem.getLayout()))
			writer.endElement("div");
	}

}
