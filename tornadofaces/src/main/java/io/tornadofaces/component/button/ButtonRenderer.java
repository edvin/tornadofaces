package io.tornadofaces.component.button;

import io.tornadofaces.component.CoreRenderer;
import io.tornadofaces.component.util.ComponentUtils;
import io.tornadofaces.component.util.GlobalId;
import io.tornadofaces.component.util.StyleClass;
import io.tornadofaces.util.WidgetBuilder;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.context.ResponseWriter;
import javax.faces.event.ActionEvent;
import javax.faces.render.FacesRenderer;
import java.io.IOException;

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

		Boolean disabled = button.isDisabled();
		if (disabled)
			writer.writeAttribute("disabled", "disabled", null);

		StyleClass.of(button.getStyleClass())
			.add(button.getSize())
			.add(button.getColor())
			.add("hollow", button.isHollow())
			.add("expand", button.isExpand())
			.add("button", button.getTreatAsButton())
			.write(writer);
	}

	public boolean getRendersChildren() {
		return true;
	}

	public void encodeChildren(FacesContext context, UIComponent component) throws IOException {
		ButtonBase button = (ButtonBase) component;
		ResponseWriter writer = context.getResponseWriter();

		Object value = button.getValue();

		if (value != null) {
			if (button instanceof CommandButton && ((CommandButton) button).isInput())
				writer.writeAttribute("value", value, null);
			else
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
			.attr("render", GlobalId.resolveIdString(context, button.getRender()))
			.attr("execute", GlobalId.resolveIdString(context, button.getExecute()))
			.attr("onsuccess", button.getOnsuccess())
			.attr("oncomplete", button.getOncomplete())
			.attr("onstart", button.getOnstart());

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

		decodeBehaviors(context, component);
	}

}
