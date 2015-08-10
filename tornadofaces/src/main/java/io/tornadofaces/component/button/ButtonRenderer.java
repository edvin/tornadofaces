package io.tornadofaces.component.button;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.Coalesce;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.FacesRenderer;
import java.io.IOException;
import java.util.Arrays;

@FacesRenderer(rendererType = ButtonRenderer.RENDERER_TYPE, componentFamily = ComponentUtils.COMPONENT_FAMILY)
public class ButtonRenderer extends CoreRenderer {
	public static final String RENDERER_TYPE = "io.tornadofaces.component.ButtonRenderer";

	public void encodeBegin(FacesContext context, UIComponent component) throws IOException {
		ResponseWriter writer = context.getResponseWriter();
		ButtonBase button = (ButtonBase) component;

		writer.startElement(button.getTagName(), button);
		writer.writeAttribute("id", button.getClientId(context), null);

		if (button instanceof CommandButton)
			writer.writeAttribute("type", "submit", null);
		else if (button instanceof CommandLink)
			writer.writeAttribute("href", "#", null);

		String style = button.getStyle();
		if (style != null)
			writer.writeAttribute("style", style, null);

		String title = button.getTitle();
		if (title != null)
			writer.writeAttribute("title", title, null);

		Boolean disabled = button.isDisabled();
		if (disabled)
			writer.writeAttribute("disabled", "disabled", null);

		StyleClass.of(button.getStyleClass())
			.add(button.getSize())
			.add(button.getColor())
			.add("hollow", button.isHollow())
			.add("expand", button.isExpand())
			.add("button", button.getTreatAsButton())
			.add("is-hidden", !button.getShow())
			.write(writer);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ButtonBase button = (ButtonBase) component;
		ResponseWriter writer = context.getResponseWriter();

		Object value = button.getValue();

		if (value != null && button instanceof CommandButton && ((CommandButton) button).isInput()) {
			writer.writeAttribute("value", value, null);
		} else {
			String icon = button.getIcon();

			if (icon != null) {
				writer.startElement("i", button);
				writer.writeAttribute("class", icon, null);
				writer.endElement("i");
				writer.write(" ");
			}

			if (value != null)
				writer.writeText(value, "value");
		}

		super.encodeChildren(context, component);
	}

	public void encodeEnd(FacesContext context, UIComponent component) throws IOException {
		ButtonBase button = (ButtonBase) component;
		ResponseWriter writer = context.getResponseWriter();

		writer.endElement(button.getTagName());

		WidgetBuilder builder = new WidgetBuilder(context, button)
			.init()
			.attr("render", ComponentUtils.resolveIds(context, button, Arrays.asList(button.getRender().split(" "))))
			.attr("execute", ComponentUtils.resolveIds(context, button, Arrays.asList(button.getExecute().split(" "))))
			.attr("onsuccess", button.getOnsuccess())
			.attr("oncomplete", button.getOncomplete())
			.nativeAttr("onerror", button.getOnerror())
			.attr("onbegin", button.getOnbegin())
			.attr("beforebegin", button.getBeforebegin());

		if (button.getTreatAsButton())
			builder
				.attr("spinnerStyle", button.getSpinnerStyle())
				.attr("spinnerColor", button.getSpinnerColor())
				.attr("spinnerSize", button.getSpinnerSize());

		builder.finish();
	}

	public void decode(FacesContext context, UIComponent component) {
		ButtonBase button = (ButtonBase) component;
		if (button.isDisabled())
			return;

		String clientId = component.getClientId();
		String source = context.getExternalContext().getRequestParameterMap().get("javax.faces.source");

		if (clientId.equals(source))
			component.queueEvent(new ActionEvent(component));

		super.decode(context, component);
	}

}
