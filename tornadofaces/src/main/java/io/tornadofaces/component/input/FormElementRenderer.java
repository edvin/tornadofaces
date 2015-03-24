package io.tornadofaces.component.input;

import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.UIInput;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.render.FacesRenderer;
import javax.faces.render.Renderer;
import java.io.IOException;
import java.util.List;

@FacesRenderer(rendererType = FormElementRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class FormElementRenderer extends Renderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.FormElementRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		FormElement elem = (FormElement) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.startElement("div", elem);
		String id = elem.getId();
		if (!id.startsWith("j_idt"))
			writer.writeAttribute("id", elem.getClientId(context), null);

		StyleClass styleClass = StyleClass
			.of(elem.getLayout())
			.add("padding", elem.isPadding())
			.add("label-left", FormElement.LabelPosition.left.equals(elem.getLabelPosition()))
			.add(elem.getStyleClass());

		String style = elem.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

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

		String label = elem.getLabel();

		if (label != null) {
			writer.startElement("label", component);
			UIInput input = ComponentUtils.getFirstInputChild(component);
			if (input != null)
				writer.writeAttribute("for", input.getClientId(), null);
			
			writer.write(label);
			writer.endElement("label");
		}

		if (elem.shouldRenderInlineLabelSpan()) {
			writer.startElement("span", component);
			writer.writeAttribute("class", "inline-label", null);
		}

		String prefix = elem.getPrefix();
		if (prefix != null) {
			writer.startElement("span", elem);
			writer.writeAttribute("class", "form-label", null);
			writer.writeText(prefix, "prefix");
			writer.endElement("span");
		}
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		FormElement elem = (FormElement) component;
		ResponseWriter writer = context.getResponseWriter();

		String suffix = elem.getSuffix();
		if (suffix != null) {
			writer.startElement("span", elem);
			writer.writeAttribute("class", "form-label", null);
			writer.writeText(suffix, "suffix");
			writer.endElement("span");
		}

		if (elem.shouldRenderInlineLabelSpan())
			writer.endElement("span");

		UIInput input = ComponentUtils.getFirstInputChild(component);

		if (input != null) {
			List<FacesMessage> messages = context.getMessageList(input.getClientId(context));
			if (!messages.isEmpty()) {
				for (FacesMessage m : messages) {
					writer.startElement("span", component);
					String type = m.getSeverity().toString().toLowerCase();
					if (type.contains(" "))
						type = type.substring(0, type.indexOf(" "));
					
					writer.writeAttribute("class", "field-" + type + "-message", null);
					writer.write(m.getSummary());
					writer.endElement("span");
				}
			}
		}

		writer.endElement("div");
	}

}
