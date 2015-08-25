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
import java.util.ArrayList;
import java.util.List;

import static io.tornadofaces.component.input.FormElement.LabelPosition.left;

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

		Boolean show = elem.isShow();

		StyleClass styleClass = StyleClass
			.of("form-element")
			.add(elem.getLayout())
			.add("padding", elem.isPadding())
			.add("label-left", left.equals(elem.getLabelPosition()))
			.add("is-hidden", !show)
			.add(elem.getStyleClass());

		if (!show)
			return;

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
			StyleClass.of(elem.getLabelClass()).write(writer);
			UIInput input = ComponentUtils.getFirstInputChild(component);
			if (input != null)
				writer.writeAttribute("for", input.getClientId(), null);

			String quickHelp = elem.getQuickhelp();

			if (!quickHelp.isEmpty()) {
				writer.startElement("a", component);
				writer.writeAttribute("class", "icon-help label-help", null);
				writer.writeAttribute("onclick", "Quickhelp.show(this, '" + quickHelp + "')", null);
				writer.endElement("a");
			}

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

		if (elem.isShow()) {

			String suffix = elem.getSuffix();
			if (suffix != null) {
				writer.startElement("span", elem);
				writer.writeAttribute("class", "form-label", null);
				writer.writeText(suffix, "suffix");
				writer.endElement("span");
			}

			if (elem.shouldRenderInlineLabelSpan())
				writer.endElement("span");

			// Gather input messages for FormElement plus first child
			List<FacesMessage> messages = new ArrayList<>();
			messages.addAll(context.getMessageList(elem.getClientId(context)));
			UIInput input = ComponentUtils.getFirstInputChild(component);
			if (input != null)
				messages.addAll(context.getMessageList(input.getClientId(context)));

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
